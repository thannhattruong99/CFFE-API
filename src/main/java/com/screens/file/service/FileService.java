package com.screens.file.service;

import com.common.service.BaseService;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.*;
import com.listeners.events.EventPublisher;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.form.ResponseUploadVideo;
import com.util.FileHelper;
import com.util.GCPHelper;
import com.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.util.PathConstant.*;

@Service
public class FileService extends BaseService {



    // upload image
    public ResponseUploadImage uploadImageToStorage(MultipartFile file) {
        ResponseUploadImage response = new ResponseUploadImage();

        // check size file
        if(file.isEmpty() || file.getSize()==0){
            response.setErrorCodes(getError(MessageConstant.MSG_112));
        }
        // check type file
        else if(!(file.getContentType().toLowerCase().equals("image/jpg")
                || file.getContentType().toLowerCase().equals("image/jpeg")
                || file.getContentType().toLowerCase().equals("image/png"))){
            response.setErrorCodes(getError(MessageConstant.MSG_113));
        }
        if (response.getErrorCodes() != null) {
            return response;
        }

        // upload file to server
        String fileName = FileHelper.storeFileOnServer(file, RESOURCE_PATH + IMAGE_FOLDER_SERVER);
        if (fileName.isEmpty()) {
            response.setErrorCodes(getError(MessageConstant.MSG_114));
            return response;
        }
        // get file on server upload to storage
        try {
            response.setFilePath(GCPHelper.uploadFile(IMAGE_FOLDER_SERVER + fileName,
                    IMAGE_FOLDER_CLOUD + StringUtils.cleanPath(fileName)));
            FileHelper.deleteFile(IMAGE_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            System.out.println("Toang roi ne: " + e.getMessage());
        }
        return response;
    }

    public ResponseUploadVideo uploadVideoToStorage(MultipartFile[] files) throws IOException {
        ResponseUploadVideo response = new ResponseUploadVideo();

        // check size file
        for(MultipartFile file : files) {
            if(file.isEmpty() || file.getSize()==0){
                response.setErrorCodes(getError(MessageConstant.MSG_119));
            }
            // check type file
            else if(!file.getContentType().toLowerCase().equals("video/mp4")){
                response.setErrorCodes(getError(MessageConstant.MSG_118));
            }
            if (response.getErrorCodes() != null) {
                return response;
            }
        }

        // upload file to server
        List<String> listFileName = new ArrayList<>();
        for(MultipartFile file : files) {
            String fileName = FileHelper.storeFileOnServer(file, RESOURCE_PATH + VIDEO_FOLDER_SERVER);
            if (fileName.isEmpty()) {
                response.setErrorCodes(getError(MessageConstant.MSG_114));
                return response;
            } else {
                listFileName.add(fileName);
                String filePath = FileHelper.getResourcePath() + VIDEO_FOLDER_SERVER + fileName;
                getVideoProperties(filePath);
            }
        }

        // up file on server upload to storage
        List<String> listOutputPath = new ArrayList<>();
        listFileName.forEach(fileName -> {
            try {
                String outputPath = GCPHelper.uploadFile(VIDEO_FOLDER_SERVER + fileName,
                        VIDEO_FOLDER_CLOUD + StringUtils.cleanPath(fileName));
                listOutputPath.add(outputPath);
                FileHelper.deleteFile(VIDEO_FOLDER_SERVER + fileName);
            } catch (IOException e) {
                System.out.println("Upload video taong: " + e.getMessage());
            }
        });
        response.setFilePath(listOutputPath);
        return response;
    }

    public String testVoid(){
        String temp = "aaaa";

        return temp;
    }
    private void getVideoProperties(String filePath) throws IOException {
        IsoFile isoFile = new IsoFile(filePath);
        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();
        System.out.println("FilePath: " + filePath);
        System.out.println("Type: " + mhb.getType());
        System.out.println("Current Time: " + mhb.getCurrentTime());
        System.out.println("Creation Time: " + mhb.getCreationTime());
        System.out.println("Modified Time: " + mhb.getModificationTime());
        System.out.println("Durration (s): " + mhb.getDuration() / mhb.getTimescale());
    }

    private List<String> getError(String errorCode){
        List<String> errorList = new ArrayList<>();
        errorList.add(errorCode);
        return errorList;
    }
}
