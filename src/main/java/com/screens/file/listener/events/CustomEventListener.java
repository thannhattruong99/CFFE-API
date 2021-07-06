package com.screens.file.listener.events;

import com.common.service.BaseService;
import com.screens.file.listener.detector.DetectService;
import com.screens.file.dto.VideoProperty;
import com.screens.file.listener.detector.EmotionDTO;
import com.screens.file.service.FileService;
import com.screens.video.dao.VideoDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CustomEventListener extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(CustomEventListener.class);

    private Map<String, EventCreator> eventCreatorMap;

    @Autowired
    private VideoDAO videoDAO;


    private static final String CONTENT_TYPE_IMAGE = "";
    private static final String CONTENT_TYPE_VIDEO = "video/mp4";

    /**
     * EVENT LISTENER
     * @param eventCreator
     * @throws InterruptedException
     */
    @Async
    @EventListener
    public void eventListener(EventCreator eventCreator) throws InterruptedException {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }
        // START EVENT
        eventCreator.setStatus(0);
        eventCreator.setMessage("Loading");
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);

        // DO EVENT
        List<String> videoErrorNameList = new ArrayList<>();
        for (VideoProperty videoProperty: eventCreator.getVideoPropertyList()) {
            try{
                int countHP;
                // DETECT VIDEO HOT SPOT / EMOTION
                if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                    if((countHP = DetectService.countPerson(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != 0){
                        videoProperty.setTotalPerson(countHP);
                    } else {
                        setError(videoProperty,videoErrorNameList,eventCreator);
                    }
                }
                if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                    EmotionDTO emotionDTO;
                    if((emotionDTO = DetectService.countEmotion(videoProperty.getVideoNameUUID(),
                            videoProperty.getVideoNameUUID())) != null){
                        videoProperty.setEmotions(emotionDTO);
                        System.out.println("OUTPUT DETECT: " + emotionDTO.toString());
                    } else {
                        setError(videoProperty,videoErrorNameList,eventCreator);
                    }
                }

                // DELETE FILE INPUT
                FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());

                // UPLOAD STORAGE CLOUD / INSERT DATABASE
                if (videoProperty.getStatusId() != -1) {
                    uploadVideoDetectedToStorage(videoProperty);
                    insertDatabase(videoProperty,videoErrorNameList,eventCreator);
                }
            } catch (InterruptedException e) {
                setError(videoProperty,videoErrorNameList,eventCreator);
            } catch (IOException e) {
                setError(videoProperty,videoErrorNameList,eventCreator);
            }
        }

        // END OF EVENT
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

    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }

    private void uploadVideoDetectedToStorage(VideoProperty videoProperty) {
        try {
            String outputPath = GCPHelper.uploadFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID(),
                    VIDEO_FOLDER_CLOUD + org.springframework.util.StringUtils.cleanPath(videoProperty.getVideoNameUUID()),
                    CONTENT_TYPE_VIDEO);
            videoProperty.setVideoUrl(outputPath);
            FileHelper.deleteFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
        } catch (IOException e) {
            System.out.println("Upload video toang: " + e.getMessage());
        }
    }

    private void insertDatabase(VideoProperty videoProperty, List<String> videoErrorNameList, EventCreator eventCreator){
        try {
            if (DETECT_HOT_SPOT == videoProperty.getTypeVideo()) {
                videoDAO.insertHotSpot(videoProperty);
            }
            if (DETECT_EMOTION == videoProperty.getTypeVideo()) {
                videoDAO.insertEmotion(videoProperty);
            }
            videoDAO.insertVideoProperty(videoProperty);
        } catch (PersistenceException e){
            logger.error("Error at CustomEventListener: " + e.getMessage());
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

}
