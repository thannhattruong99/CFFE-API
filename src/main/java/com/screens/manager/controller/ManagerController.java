package com.screens.manager.controller;

import com.common.form.ResponseCommonForm;
import com.screens.manager.form.*;
import com.screens.manager.service.ManagerService;
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

    @RequestMapping(value = "/managers", method = RequestMethod.POST)
    public String getManagers(@Validated @RequestBody RequestManagerListForm requestManagerListForm, //
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

        return ResponseSupporter.resonpseResult(response);
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

        return ResponseSupporter.resonpseResult(response);
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

        return ResponseSupporter.resonpseResult(true);
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

        return ResponseSupporter.resonpseResult(true);
    }

    @GetMapping(value = "/manager/reset_password")
    public String resetPassword(@Validated RequestResetPasswordForm requestForm, //
                                       BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.resetManagerPassword(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.resonpseResult(true);
    }

    @RequestMapping(value = "/manager/update_status", method = RequestMethod.POST)
    public String updateStatus(@Validated @RequestBody RequestUpdateStatusForm requestForm, //
                               BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = managerService.updateManagerStatus(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.resonpseResult(true);
    }
}
