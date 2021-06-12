package com.screens.file.service;

import com.listeners.events.EventPublisher;
import com.screens.file.form.ResponseUploadImage;
import com.util.FileHelper;
import com.util.GCPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final static String PREFIX_IMAGE_PATH = "/src/main/resources/images";
    private final static String IMAGE_FOLDER = "images/";

    // upload image
    public ResponseUploadImage storeImage(MultipartFile file) {
        ResponseUploadImage response = new ResponseUploadImage();
        String fileName = FileHelper.storeImage(file, PREFIX_IMAGE_PATH);
        try {
            response.setFilePath(GCPHelper.uploadImage(IMAGE_FOLDER + fileName,
                    IMAGE_FOLDER + StringUtils.cleanPath(file.getOriginalFilename())));
            FileHelper.deleteFile(IMAGE_FOLDER + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
