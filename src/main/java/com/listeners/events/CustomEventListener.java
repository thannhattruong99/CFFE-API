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
    public void eventListener(EventCreator eventCreator) throws InterruptedException, IOException {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }

//        // TODO: detect video
//        // TODO: insert tblHotspot
//        eventCreator.setStatus(11);
//        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);
//        Thread.sleep(10000);
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


//        int count;
//        if((count = PythonHelper.countPerson("videos/input/" + eventCreator.getRelativeFilePath(),
//                "videos/output/" + eventCreator.getRelativeFilePath())) != 0){
//
//            GCPHelper.uploadImage("videos/output/" + eventCreator.getRelativeFilePath());
//
//            System.out.println("Total person input: " + count);
//            FileHelper.deleteFile("videos/input/" + eventCreator.getRelativeFilePath());
//            FileHelper.deleteFile("videos/output/" + eventCreator.getRelativeFilePath());
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
                String outputPath = GCPHelper.uploadFile(VIDEO_FOLDER_SERVER + videoProperty.getVideoName(),
                        VIDEO_FOLDER_CLOUD + StringUtils.cleanPath(videoProperty.getVideoName()));
                videoProperty.setVideoUrl(outputPath);
                FileHelper.deleteFile(VIDEO_FOLDER_SERVER + videoProperty.getVideoName());
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
