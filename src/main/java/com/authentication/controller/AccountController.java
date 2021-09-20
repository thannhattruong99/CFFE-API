package com.authentication.controller;

import com.authentication.form.RequestLoginForm;
import com.authentication.form.ResponseLoginForm;
import com.authentication.service.AccountService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    private static final String MSG_003 = "MSG-003";

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String login(@RequestBody RequestLoginForm authenticationRequest) {

        ResponseLoginForm response = accountService
                .checkLogin(authenticationRequest);
        if(response != null){
            return ResponseSupporter.responseResult(response);
        }
        List<String> errorCodes = new ArrayList<>();
        errorCodes.add(MSG_003);
        return ResponseSupporter.responseErrorResult(errorCodes);
    }
}
