package com.screens.file.service;

import com.common.service.BaseService;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.*;
import com.googlecode.mp4parser.DataSource;
import com.screens.file.form.ResponseUploadImage;
import com.util.FileHelper;
import com.util.GCPHelper;
import com.util.MessageConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            response.setFilePath(GCPHelper.uploadImage(IMAGE_FOLDER_SERVER + fileName,
                    IMAGE_FOLDER_CLOUD + StringUtils.cleanPath(fileName)));
            FileHelper.deleteFile(IMAGE_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public ResponseUploadImage uploadVideoToStorage(MultipartFile file) throws IOException {
        ResponseUploadImage response = new ResponseUploadImage();
        List<String> errorMsg = new ArrayList<>();
        // check size file
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

        // upload file to server
        String fileName = FileHelper.storeFileOnServer(file, RESOURCE_PATH + VIDEO_FOLDER_SERVER);
        if (fileName.isEmpty()) {
            errorMsg.add(MessageConstant.MSG_114);
            response.setErrorCodes(errorMsg);
            return response;
        }

        String filePath = FileHelper.getResourcePath() + VIDEO_FOLDER_SERVER + fileName;
        getVideoProperties(filePath);

        // get file on server upload to storage
        try {
            response.setFilePath(GCPHelper.uploadImage(VIDEO_FOLDER_SERVER + fileName,
                    VIDEO_FOLDER_CLOUD + StringUtils.cleanPath(fileName)));
            FileHelper.deleteFile(VIDEO_FOLDER_SERVER + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void getVideoProperties(String filePath) throws IOException {
        File videoFile = new File(filePath);
        IsoFile isoFile = new IsoFile(filePath);
        MovieHeaderBox mhb = isoFile.getMovieBox().getMovieHeaderBox();

        long temp = mhb.getDuration() / mhb.getTimescale();
        System.out.println(mhb.toString());
    }

    private List<String> getError(String errorCode){
        List<String> errorList = new ArrayList<>();
        errorList.add(errorCode);
        return errorList;
    }
}
