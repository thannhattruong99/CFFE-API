package com.screens.camera.controller;

import com.common.form.ResponseCommonForm;
import com.screens.camera.form.*;
import com.screens.camera.service.CameraService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin")
public class CameraController {
    private static final String MSG_009 = "MSG-009";
    private static final String MSG_063 = "MSG-063";

    @Autowired
    private CameraService cameraService;

    @RequestMapping(value = "/available-camera-lst", method = RequestMethod.GET)
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

    @RequestMapping(value = "/cameras", method = RequestMethod.GET)
    public String getCameras(@Validated RequestCameraListForm requestForm,
                             BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCameraListForm responseForm = cameraService.getCameraList(requestForm);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_063);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(responseForm);
    }

    @RequestMapping(value = "/camera", method = RequestMethod.GET)
    public String getCameraDetail(@Validated RequestCameraDetailForm requestForm,
                             BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCameraDetailForm responseForm = cameraService.getCameraDetail(requestForm);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.resonpseResult(responseForm);
    }

    @RequestMapping(value = "/camera/create", method = RequestMethod.POST)
    public String createCamera(@Validated @RequestBody RequestCreateCameraForm requestForm,
                               BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = cameraService.createCamera(requestForm);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.resonpseResult(true);
    }

    @RequestMapping(value = "/camera/update", method = RequestMethod.POST)
    public String updateCamera(@Validated @RequestBody RequestUpdateCameraForm requestForm,
                               BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = cameraService.updateCamera(requestForm);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.resonpseResult(true);
    }

    @RequestMapping(value = "/camera/update-status", method = RequestMethod.POST)
    public String updateStatus(@Validated @RequestBody RequestUpdateCameraStatusForm requestForm,
                               BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm responseForm = cameraService.updateCameraStatus(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.resonpseResult(true);
    }
}
