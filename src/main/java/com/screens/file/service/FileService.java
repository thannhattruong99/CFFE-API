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
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final static String SUFFIX_IMAGE_PATH = "/src/main/resources/images";
    private final static String IMAGE_FOLDER_CLOUD = "images/";
    private final static String IMAGE_FOLDER_SERVER = "images/";

    // upload image
    public ResponseUploadImage uploadImageToStorage(MultipartFile file) {
        ResponseUploadImage response = new ResponseUploadImage();
        List<String> errorMsg = new ArrayList<>();

        // check size file
        if(file.isEmpty() || file.getSize()==0){
            errorMsg.add("MSG-112");
            response.setErrorCodes(errorMsg);
        }
        // check type file
        else if(!(file.getContentType().toLowerCase().equals("image/jpg")
                || file.getContentType().toLowerCase().equals("image/jpeg")
                || file.getContentType().toLowerCase().equals("image/png"))){
            errorMsg.add("MSG-113");
            response.setErrorCodes(errorMsg);
        }
        if (response.getErrorCodes() != null) {
            return response;
        }

        // upload file to server
        String fileName = FileHelper.storeImageOnServer(file, SUFFIX_IMAGE_PATH);
        if (fileName.isEmpty()) {
            errorMsg.add("MSG-114");
            response.setErrorCodes(errorMsg);
            return response;
        }
        // get file on server upload to storage
        try {
            response.setFilePath(GCPHelper.uploadImage(IMAGE_FOLDER_SERVER + fileName,
                    IMAGE_FOLDER_CLOUD + StringUtils.cleanPath(fileName)));
            FileHelper.deleteFile(IMAGE_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
