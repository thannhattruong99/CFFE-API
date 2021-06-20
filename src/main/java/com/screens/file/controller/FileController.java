package com.screens.file.controller;


import com.listeners.events.EventPublisher;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.form.ResponseUploadVideo;
import com.screens.file.service.FileService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController("")
@RequestMapping("/file")
@SecurityRequirement(name = "basicAuth")
public class FileController {
    private static final String MSG_111 = "MSG-011";

    @Autowired
    private EventPublisher eventPublisher;

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

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/upload-video")
    public String uploadVideo(@RequestParam("file") MultipartFile[] files) throws IOException {
        ResponseUploadVideo response = fileService.uploadVideoToStorage(files);
        if(response.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(response.getErrorCodes());
        }
        eventPublisher.publishEvent("HELLLLLLOOOOOOOO","/test/test.test");
        return ResponseSupporter.responseResult(response);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/upload-video-version2")
    public String uploadVideo2() throws IOException {
        String response = fileService.testVoid();
        //http://localhost:9090/admin/manager/store/test-sse
        return ResponseSupporter.responseResult(response);
    }

}
