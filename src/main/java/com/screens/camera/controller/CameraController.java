package com.screens.camera.controller;

import com.common.form.ResponseCommonForm;
import com.filter.dto.AuthorDTO;
import com.screens.camera.form.*;
import com.screens.camera.service.CameraService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class CameraController {
    private static final String MSG_009 = "MSG-009";
    private static final String MSG_020 = "MSG-020";
    private static final String AUTHOR = "AUTHOR";

    @Autowired
    private CameraService cameraService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
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

        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/cameras", method = RequestMethod.GET)
    public String getCameras(@Validated RequestCameraListForm requestForm,
                             BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);
        ResponseCameraListForm responseForm = cameraService.getCameraList(requestForm, authorDTO);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_020);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/camera", method = RequestMethod.GET)
    public String getCameraDetail(@Validated RequestCameraDetailForm requestForm,
                             BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);
        ResponseCameraDetailForm responseForm = cameraService.getCameraDetail(requestForm, authorDTO);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
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

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/camera/update", method = RequestMethod.POST)
    public String updateCamera(@Validated @RequestBody RequestUpdateCameraForm requestForm,
                               BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = cameraService.updateCamera(requestForm);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
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
        return ResponseSupporter.responseResult(true);
    }
}