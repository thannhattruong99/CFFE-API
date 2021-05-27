package com.screens.camera.controller;

import com.screens.camera.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("admin/manager/store/shlef")
public class CameraController {

    @Autowired
    private CameraService cameraService;
}
