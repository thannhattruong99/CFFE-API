package com.screens.manager.controller;

import com.common.form.ResponseCommonForm;
import com.screens.manager.form.*;
import com.screens.manager.service.ManagerService;
import com.util.GCPHelper;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin")
public class ManagerController {
    private static final String MSG_009 = "MSG-009";
    private static final String MSG_063 = "MSG-063";

    @Autowired
    private ManagerService managerService;

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

    @GetMapping(value = "/manager")
    public String getManagerDetail(@Valid RequestManagerDetailForm requestForm, BindingResult result) {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseManagerDetailForm response = managerService.getManagerDetail(requestForm);
        if(response == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_063);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(response);
    }

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