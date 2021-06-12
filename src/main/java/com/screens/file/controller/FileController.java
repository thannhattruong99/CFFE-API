package com.screens.file.controller;


import com.common.form.UploadFileResponse;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.service.FileService;
import com.screens.shelf.service.ShelfService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file) {

        ResponseUploadImage response = fileService.storeImage(file);
        if(response != null){
            return ResponseSupporter.responseResult(response);
        }
        return ResponseSupporter.responseResult("loi roi");
    }

}
