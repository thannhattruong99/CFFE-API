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
//        Thread.sleep(1000);
        System.out.println("111111111111111111111111111");
        Thread.sleep(10000);
        if(eventCreatorMap == null){
            eventCreatorMap = new HashMap<>();
        }
        System.out.println("222222222222222222222222222");
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


}
