package com.batches.events;

import com.util.FileHelper;
import com.util.GCPHelper;

import java.io.IOException;

public class ThreadHelper extends Thread{
    public void run() {
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (true) {
            System.out.println(this.getName() + ": New Thread is running..." + i++);
            try {
                if(FileHelper.checkExistFile("videos/example_01.avi")){
                    if(GCPHelper.uploadImage("videos/example_01.avi") != null){
                        FileHelper.deleteFile("videos/example_01.mp4");
                        FileHelper.deleteFile("videos/example_01.avi");
                    }
                }

                //Wait for one sec so it doesn't print too fast
//                Thread.sleep(5000);
            } catch (IOException e) {

            }
        }
    }
}
