package com.screens.file.listener.events;

import com.screens.file.listener.detector.DetectService;
import com.screens.file.dto.VideoProperty;
import com.screens.file.listener.detector.EmotionDTO;
import com.screens.video.dao.VideoDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.util.PathConstant.*;

@Component
public class CustomEventListener {
    private Map<String, EventCreator> eventCreatorMap;

    @Autowired
    private VideoDAO videoDAO;

    private static final int DETECT_HOT_SPOT = 1;
    private static final int DETECT_EMOTION = 2;
    private static final String CONTENT_TYPE_IMAGE = "";
    private static final String CONTENT_TYPE_VIDEO = "video/mp4";

    @Async
    @EventListener
    public void eventListener(EventCreator eventCreator) throws InterruptedException {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }
        System.out.println("Start do event 1");
        eventCreator.setStatus(0);
        eventCreator.setMessage("Loading");
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);

        List<String> videoErrorNameList = new ArrayList<>();
        for (VideoProperty videoProperty: eventCreator.getVideoPropertyList()) {
            int countHP;
            try{
                // TODO: detect video hot spot / emotion
                if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                    if((countHP = DetectService.countPerson(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != 0){
                        videoProperty.setTotalPerson(countHP);
                    }
                }
                if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                    EmotionDTO emotionDTO;
                    if((emotionDTO = DetectService.countEmotion(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != null){
//                        videoProperty.setTotalPerson(countHP);
                        System.out.println("count emotion");
                    }
                }

                // TODO: delete file input
                FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());

                // TODO: upload video moi => cloud
                uploadVideoDetectedToStorage(videoProperty);

                // TODO: insert tblHotspot / tblEmotion
                // TODO: insert tblVideo
                insertDatabase(videoProperty,videoErrorNameList,eventCreator);

            }catch (InterruptedException e) {
                setError(videoProperty,videoErrorNameList,eventCreator);
            } catch (IOException e) {
                setError(videoProperty,videoErrorNameList,eventCreator);
            }
        }

        for (VideoProperty video: eventCreator.getVideoPropertyList()) {
            System.out.println("Video name: " + video.getVideoNameUUID());
            System.out.println("Video status: " + video.getStatusId());
            System.out.println("Total person: " + video.getTotalPerson());
        }


        eventCreator.setStatus(1);
        String msg = "";
        if (videoErrorNameList.size() >0){
            msg = " - " + videoErrorNameList.size() +" error: ";
            for (String name : videoErrorNameList) {
                msg += "["+ name + "]";
            }
        }
        eventCreator.setMessage("Success" + msg);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
    }

    private void uploadVideoDetectedToStorage(VideoProperty videoProperty) {
        try {
            String outputPath = GCPHelper.uploadFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID(),
                    VIDEO_FOLDER_CLOUD + org.springframework.util.StringUtils.cleanPath(videoProperty.getVideoNameUUID()),
                    CONTENT_TYPE_VIDEO);
            videoProperty.setVideoUrl(outputPath);
//            FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
            FileHelper.deleteFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
        } catch (IOException e) {
            System.out.println("Upload video taong: " + e.getMessage());
        }
    }

    private void insertDatabase(VideoProperty videoProperty, List<String> videoErrorNameList, EventCreator eventCreator){
        if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
            String shelfCameraMappingId = videoDAO.getShelfCameraMappingId(videoProperty);
            System.out.println("shelfCameraMappingId = " + shelfCameraMappingId);
            if (StringUtils.isNotEmpty(shelfCameraMappingId)){
                videoProperty.setShelfCameraMappingId(shelfCameraMappingId);
                videoDAO.insertHotSpot(videoProperty);
                videoDAO.insertVideoProperty(videoProperty);
            } else {
                setError(videoProperty,videoErrorNameList,eventCreator);
            }
        }
        if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
//                    videoDAO.insertEmotion(videoProperty);
//                    videoDAO.insertVideoProperty(videoProperty);
        }
    }
    private void setError(VideoProperty videoProperty, List<String> videoErrorNameList, EventCreator eventCreator) {
        videoProperty.setStatusId(-1);;
        videoErrorNameList.add(videoProperty.getVideoNameOriginal());
        String msg = "";
        for (String name : videoErrorNameList) {
            msg += "["+ name + "]";
        }
        eventCreator.setMessage("Loading - " + videoErrorNameList.size() +" error: " + msg );
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
    }

    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }
}
