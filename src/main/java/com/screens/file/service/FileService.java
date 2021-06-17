package com.screens.file.service;

import com.common.service.BaseService;
import com.screens.file.form.ResponseUploadImage;
import com.util.FileHelper;
import com.util.GCPHelper;
import com.util.MessageConstant;
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
        List<String> errorMsg = new ArrayList<>();

        // check size file
        if(file.isEmpty() || file.getSize()==0){
            errorMsg.add(MessageConstant.MSG_112);
            response.setErrorCodes(errorMsg);
        }
        // check type file
        else if(!(file.getContentType().toLowerCase().equals("image/jpg")
                || file.getContentType().toLowerCase().equals("image/jpeg")
                || file.getContentType().toLowerCase().equals("image/png"))){
            errorMsg.add(MessageConstant.MSG_113);
            response.setErrorCodes(errorMsg);
        }
        if (response.getErrorCodes() != null) {
            return response;
        }

        // upload file to server
        String fileName = FileHelper.storeImageOnServer(file, RESOURCE_PATH + IMAGE_FOLDER_SERVER);
        if (fileName.isEmpty()) {
            errorMsg.add(MessageConstant.MSG_114);
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
