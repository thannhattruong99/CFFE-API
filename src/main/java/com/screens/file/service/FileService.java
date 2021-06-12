package com.screens.file.service;

import com.listeners.events.EventPublisher;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    // upload image
    public String storeImage(MultipartFile file) {
        String fileName = FileHelper.storeImage(file);
        String resultURL = null;
        try {
            resultURL = GCPHelper.uploadImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultURL;
    }


    // TODO: upload video
}
