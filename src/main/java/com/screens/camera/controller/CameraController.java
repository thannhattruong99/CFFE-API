package com.screens.camera.controller;

import com.screens.camera.form.RequestAvailableCameraListForm;
import com.screens.camera.form.ResponseAvailableCameraListForm;
import com.screens.camera.service.CameraService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin/store")
public class CameraController {
    private static final String MSG_009 = "MSG-009";

    @Autowired
    private CameraService cameraService;

    @RequestMapping(value = "/available-shelf-lst", method = RequestMethod.GET)
    public String getAvailableCameraList(@Validated RequestAvailableCameraListForm requestForm, BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseAvailableCameraListForm responseForm = cameraService.getAvailableCameraList(requestForm);

        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(responseForm);
    }

}
