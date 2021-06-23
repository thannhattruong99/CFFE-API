package com.listeners.events;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieHeaderBox;
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
    private ShelfDAO shelfDAO;

    @Autowired
    private VideoDAO videoDAO;

    @Async
    @EventListener
    public void eventListener(EventCreator eventCreator) {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }

        for (VideoProperty video: eventCreator.getVideoPropertyList()) {
            int count;
            try{
                if((count = PythonHelper.countPerson(video.getVideoName(),
                        video.getVideoName())) != 0){

//                    GCPHelper.uploadImage(FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + video.getVideoName());
//                    FileHelper.deleteFile(FileHelper.getResourcePath() + INPUT_VIDEO_PATH + video.getVideoName());
//                    FileHelper.deleteFile(FileHelper.getResourcePath() + OUTPUT_VIDEO_PATH + video.getVideoName());
                    video.setTotalPerson(count);
                }
            }catch (InterruptedException e) {
                video.setStatusId(-1);
                System.out.println("ERROR AT HERERER: " + e.getMessage());
            }
//            catch (IOException e) {
//                video.setStatusId(-1);
//            }
        }

        for (VideoProperty video: eventCreator.getVideoPropertyList()) {
            System.out.println("Video name: " + video.getVideoName());
            System.out.println("Video status: " + video.getStatusId());
            System.out.println("Total person: " + video.getTotalPerson());
        }

//        // TODO: detect video
//        // TODO: insert tblHotspot
//        eventCreator.setStatus(11);
//        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
//
//
//        // TODO: upload video moi => cloud
//        List<VideoProperty> videoPropertyList = uploadVideoDetectedToStorage(eventCreator);
//        // TODO: insert info video to db
//        eventCreator.setVideoPropertyList(videoPropertyList);
//        if (videoDAO.insertVideoProperty(eventCreator)){
//            eventCreator.setStatus(99);
//            eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
//        } else {
//            eventCreator.setStatus(-11);
//            eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
//        }

        eventCreator.setStatus(0);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
        Thread.sleep(20000);
        eventCreator.setStatus(1);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
    }

    private List<VideoProperty> uploadVideoDetectedToStorage(EventCreator eventCreator) {
        List<VideoProperty> videoPropertyList = eventCreator.getVideoPropertyList();
        videoPropertyList.forEach(videoProperty -> {
            try {
                String outputPath = GCPHelper.uploadFile(INPUT_VIDEO_PATH + videoProperty.getVideoName(),
                        VIDEO_FOLDER_CLOUD + StringUtils.cleanPath(videoProperty.getVideoName()));
                videoProperty.setVideoUrl(outputPath);
                FileHelper.deleteFile(INPUT_VIDEO_PATH + videoProperty.getVideoName());
            } catch (IOException e) {
                System.out.println("Upload video taong: " + e.getMessage());
            }
        });
        return videoPropertyList;
    }


    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }
}
