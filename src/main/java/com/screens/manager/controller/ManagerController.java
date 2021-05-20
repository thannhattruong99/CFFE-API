package com.screens.manager.controller;

import com.screens.city.form.CityResponseSupporter;
import com.screens.manager.form.*;
import com.screens.manager.service.ManagerService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin")
public class ManagerController {
    private static String MSG_009 = "MSG-009";
    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "/managers", method = RequestMethod.POST)
    public String getManagers(Model model,//
                             @Validated @RequestBody RequestManagerListForm requestManagerListForm, //
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
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(response);
    }
}
