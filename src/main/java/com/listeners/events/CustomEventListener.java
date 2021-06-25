package com.listeners.events;

import com.screens.file.dto.VideoProperty;
import com.screens.shelf.dao.ShelfDAO;
import com.screens.video.dao.VideoDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
            int count;
            try{
                // TODO: detect video
                if((count = PythonHelper.countPerson(videoProperty.getVideoNameUUID(),
                        videoProperty.getVideoNameUUID())) != 0){
                    videoProperty.setTotalPerson(count);
                }
                FileHelper.deleteFile2(FileHelper.getResourcePath() + INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());

                // TODO: upload video moi => cloud
                uploadVideoDetectedToStorage(videoProperty);

                // TODO: insert tblHotspot
                //  code here
                //=======================

                // TODO: insert info video to db
                videoDAO.insertVideoProperty(videoProperty);


            }catch (InterruptedException e) {
                videoProperty.setStatusId(-1);;
                videoErrorNameList.add(videoProperty.getVideoNameOriginal());
                eventCreator.setMessage("Loading - " + videoErrorNameList.size() +" error: " + videoErrorNameList.stream().toString() );
                eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
                System.out.println("ERROR AT HERERER: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("ERROR AT HERE: " + e.getMessage());
            }
        }

        for (VideoProperty video: eventCreator.getVideoPropertyList()) {
            System.out.println("Video name: " + video.getVideoNameUUID());
            System.out.println("Video status: " + video.getStatusId());
            System.out.println("Total person: " + video.getTotalPerson());
        }


//        Thread.sleep(5000);
        eventCreator.setStatus(1);
        String msg = "Success";
        if (videoErrorNameList.size() != 0) {
            msg += " - " + videoErrorNameList.size() +" error: " + videoErrorNameList.stream().toString();
        }
        eventCreator.setMessage(msg);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
    }

    private void uploadVideoDetectedToStorage(VideoProperty videoProperty) {
        try {
            String outputPath = GCPHelper.uploadFile(OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID(),
                    VIDEO_FOLDER_CLOUD + StringUtils.cleanPath(videoProperty.getVideoNameUUID()));
            videoProperty.setVideoUrl(outputPath);
//            FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
            FileHelper.deleteFile2(FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + videoProperty.getVideoNameUUID());
        } catch (IOException e) {
            System.out.println("Upload video taong: " + e.getMessage());
        }
    }


    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }
}
