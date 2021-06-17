package com.listeners.events;

import com.screens.shelf.dao.ShelfDAO;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEventListener {
    @Autowired
    private ShelfDAO shelfDAO;

    @Async
    @EventListener
    public void eventListener(EventCreator eventHelper) throws InterruptedException, IOException {
        Thread.sleep(1000);

        int count;
        if((count = PythonHelper.countPerson("videos/input/" + eventHelper.getRelativeFilePath(),
                "videos/output/" + eventHelper.getRelativeFilePath())) != 0){

//            Thread.sleep(30000);
            GCPHelper.uploadImage("videos/output/" + eventHelper.getRelativeFilePath());
//            Thread.sleep(25000);
            System.out.println("Total person input: " + count);
            FileHelper.deleteFile("videos/input/" + eventHelper.getRelativeFilePath());
            FileHelper.deleteFile("videos/output/" + eventHelper.getRelativeFilePath());
        }

    }

    private void cleanVideo() throws IOException {
        GCPHelper.uploadImage("videos/example_01.avi");
        FileHelper.deleteFile("videos/example_01.mp4");
        FileHelper.deleteFile("videos/example_01.avi");
    }
}
