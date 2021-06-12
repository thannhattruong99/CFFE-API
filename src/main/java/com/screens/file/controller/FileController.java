package com.screens.file.controller;


import com.common.form.UploadFileResponse;
import com.screens.shelf.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
public class FileController {

    @Autowired
    private ShelfService shelfService;

    @PostMapping("/upload-image")
    public UploadFileResponse uploadImage(@RequestParam("file") MultipartFile file) {

        String fileName = shelfService.storeFile(file);
        return new UploadFileResponse(fileName, fileName,
                file.getContentType(), file.getSize());
    }


}
