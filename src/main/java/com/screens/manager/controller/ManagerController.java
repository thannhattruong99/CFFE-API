package com.screens.manager.controller;

import com.common.form.ResponseCommonForm;
import com.filter.dto.AuthorDTO;
import com.screens.manager.form.*;
import com.screens.manager.service.ManagerService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("/api/")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class ManagerController {
    private static final String MSG_009 = "MSG-009";
    private static final String MSG_063 = "MSG-063";

    @Autowired
    private ManagerService managerService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/managers", method = RequestMethod.GET)
    public String getManagers(@Validated RequestManagerListForm requestManagerListForm, //
                             BindingResult result) {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseManagerListForm response = managerService.getManagerList(requestManagerListForm);
        if(response == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.responseResult(response);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/manager")
    public String getManagerDetail(@Valid RequestManagerDetailForm requestForm, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        ResponseManagerDetailForm response = managerService.getManagerDetail(requestForm, authorDTO);
        if(response == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_063);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.responseResult(response);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/manager/create", method = RequestMethod.POST)
    public String createManager(@Validated @RequestBody RequestCreateManagerForm requestForm, //
                                BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.createManger(requestForm);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/manager/update", method = RequestMethod.POST)
    public String updateManagerInformation(@Validated @RequestBody RequestUpdateManagerForm requestForm, //
                                BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.updateManagerInformation(requestForm);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/manager/reset-password")
    public String resetPassword(@Validated RequestResetPasswordForm requestForm, //
                                       BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.resetManagerPassword(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/manager/update-status", method = RequestMethod.POST)
    public String updateStatus(@Validated @RequestBody RequestUpdateManagerStatusForm requestForm, //
                               BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.updateManagerStatus(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/manager/change-password", method = RequestMethod.POST)
    public String changePassword(@Validated @RequestBody RequestChangePasswordForm requestForm,
                                 BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.changePassword(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }
}