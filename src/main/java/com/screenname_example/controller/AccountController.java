package com.screenname_example.controller;

import com.screenname_example.form.RequestCreateAccountForm;
import com.screenname_example.form.RequestGetAccountForm;
import com.screenname_example.form.ResponseCreateAccountForm;
import com.screenname_example.form.ResponseGetAccountForm;
import com.screenname_example.service.AccountService;
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

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public String getAccount() {
        return ResponseSupporter.resonpseResult(true);
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
