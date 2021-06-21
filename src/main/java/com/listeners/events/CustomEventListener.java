package com.listeners.events;

import com.screens.shelf.dao.ShelfDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.util.PathConstant.INPUT_VIDEO_PATH;
import static com.util.PathConstant.OUTPUT_VIDEO_PATH;

@Component
public class CustomEventListener {
    private Map<String, EventCreator> eventCreatorMap;

    @Autowired
    private ShelfDAO shelfDAO;

    @Async
    @EventListener
    public void eventListener(EventCreator eventCreator) throws InterruptedException, IOException {
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }
        // upload file success => 11
        eventCreator.setStatus(11);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);

        Thread.sleep(10000);

        // detect thanh cong => 99
        eventCreator.setStatus(99);
        eventCreatorMap.put(eventCreator.getEventId(),eventCreator);





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

    }

    public Map<String, EventCreator> getEventCreatorMap() {
        return eventCreatorMap;
    }

    public void setEventCreatorMap(Map<String, EventCreator> eventCreatorMap) {
        this.eventCreatorMap = eventCreatorMap;
    }
}
