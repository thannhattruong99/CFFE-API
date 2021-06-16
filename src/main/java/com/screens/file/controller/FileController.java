package com.screens.file.controller;


import com.screens.file.form.ResponseUploadImage;
import com.screens.file.service.FileService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController("")
@RequestMapping("")
@SecurityRequirement(name = "basicAuth")
public class FileController {
    private static final String MSG_111 = "MSG-011";

    @Autowired
    private FileService fileService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        ResponseUploadImage response = fileService.uploadImageToStorage(file);
        if(response.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(response.getErrorCodes());
        }
        return ResponseSupporter.responseResult(response);
    }

}
