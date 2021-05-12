package com.screenname.controller;

import com.screenname.form.RequestCreateAccountForm;
import com.screenname.form.RequestGetAccountForm;
import com.screenname.form.ResponseCreateAccountForm;
import com.screenname.form.ResponseGetAccountForm;
import com.screenname.service.AccountService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    private static final String E001 = "E001";
    private static final String E000 = "E000";

    @RequestMapping(value = "/getAccount", method = RequestMethod.POST)
    public String getAccount(Model model,//
                                       @Validated @RequestBody RequestGetAccountForm account, //
                                       BindingResult result) {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseGetAccountForm responseGetAccountForm = accountService.getStudent(account);
        if(responseGetAccountForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(E000);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(responseGetAccountForm);
    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui-custom.html");
    }

    //    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @RequestMapping(value = "/createAnAccount", method = RequestMethod.POST)
    public String createAnAccount(Model model,//
                                  @Validated @RequestBody RequestCreateAccountForm account, //
                                  BindingResult result) {
        if (result.hasErrors()) {
            return ResponseSupporter.responseErrorResult(result);
        }

        List<String> errorCodes;

        errorCodes = accountService.checkAccountBussiness(account);
        if(errorCodes.size() > 0){
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        ResponseCreateAccountForm responseCreateAccountForm = accountService.createAnAccount(account);

        if(responseCreateAccountForm == null){
            errorCodes.add(E001);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.resonpseResult(responseCreateAccountForm);
    }
}
