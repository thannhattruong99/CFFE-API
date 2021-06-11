package com.batches.events;

import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomEventListener {

    @Async
    @EventListener
    public void eventListener(EventCreator eventHelper) throws InterruptedException, IOException {
        Thread.sleep(1000);

        System.out.println("eventHelper.getRelativeFilePath(): " + eventHelper.getRelativeFilePath());
        if(PythonHelper.countPerson("videos/input/" + eventHelper.getRelativeFilePath(),
                "videos/output/" + eventHelper.getRelativeFilePath())){
            Thread.sleep(30000);
            GCPHelper.uploadImage("videos/output/" + eventHelper.getRelativeFilePath());
            Thread.sleep(25000);
            FileHelper.deleteFile("videos/input/" + eventHelper.getRelativeFilePath());
            FileHelper.deleteFile("videos/output/" + eventHelper.getRelativeFilePath());
        }
        System.out.println("Here222222");

    }

    private void cleanVideo() throws IOException {
        GCPHelper.uploadImage("videos/example_01.avi");
        FileHelper.deleteFile("videos/example_01.mp4");
        FileHelper.deleteFile("videos/example_01.avi");
    }
}
