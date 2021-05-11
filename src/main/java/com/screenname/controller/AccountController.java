package com.screenname.controller;

import com.screenname.dto.AccountDTO;
import com.screenname.form.AccountFormValidator;
import com.screenname.service.AccountService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/getAllAccount")
    public List<AccountDTO> sayHello() {
        return accountService.getStudentAll();
    }

    @GetMapping("/getAllAccountDemo")
    public List<AccountDTO> sayHelloDemo() {
        return accountService.getStudentAll();
    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui-custom.html");
    }

    //    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @RequestMapping(value = "/createAnAccount", method = RequestMethod.POST)
    public String createAnAccount(Model model,//
                                  @Validated @RequestBody  AccountFormValidator account, //
                                  BindingResult result) {
        if (result.hasErrors()) {
            return ResponseSupporter.responseResult(ResponseSupporter.mappingErrorCodeAndMsg(result));
        }
        List<String> errorCodes;

        errorCodes = accountService.checkAccountBussiness(account);
        if(errorCodes.size() > 0){
            return ResponseSupporter.responseResult(ResponseSupporter.mappingErrorCodeAndMsg(errorCodes));
        }

        AccountDTO accountDTO = accountService.createAnAccount(account);

        if(accountDTO == null){
            errorCodes.add("E001");
            return ResponseSupporter.responseResult(ResponseSupporter.mappingErrorCodeAndMsg(errorCodes));
        }
        return ResponseSupporter.responseResult(accountDTO);
    }
}
