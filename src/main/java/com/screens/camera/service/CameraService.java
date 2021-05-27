package com.screens.camera.service;

import com.screens.camera.dao.mapper.CameraMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraService {
    @Autowired
    private CameraMapper cameraMapper;


}
