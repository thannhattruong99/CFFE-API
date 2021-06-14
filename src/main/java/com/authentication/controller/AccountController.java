package com.authentication.controller;

import com.authentication.form.RequestLoginForm;
import com.authentication.form.ResponseLoginForm;
import com.authentication.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody RequestLoginForm authenticationRequest) {

        ResponseLoginForm response = accountService
                .checkLogin(authenticationRequest);
        if(response != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui.html");
    }
}
