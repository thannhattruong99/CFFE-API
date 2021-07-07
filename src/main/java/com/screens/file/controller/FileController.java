package com.screens.file.controller;


import com.screens.file.listener.events.EventPublisher;
import com.screens.file.dto.FileTransaction;
import com.screens.file.form.ResponseUploadImage;
import com.screens.file.form.ResponseUploadVideo;
import com.screens.file.form.ResponseUploadVideoToServer;
import com.screens.file.service.FileService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

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
    public String uploadVideo(@RequestParam("file") MultipartFile[] files){
        ResponseUploadVideo response = fileService.uploadVideoToServer(files);
        if(response.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(response.getErrorCodes());
        }
        // public event Detect
        eventPublisher.publishEvent(response.getIdEvent(),response.getVideoPropertyList());
        ResponseUploadVideoToServer responseUploadVideoToServer = new ResponseUploadVideoToServer();
        responseUploadVideoToServer.setEventId(response.getIdEvent());
        return ResponseSupporter.responseResult(responseUploadVideoToServer);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping( value = "/get-file-transaction", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FileTransaction> fileTransactionEvents(@RequestParam("eventId") String eventId){
        return fileService.getFileTransactions(eventId);
    }
}
