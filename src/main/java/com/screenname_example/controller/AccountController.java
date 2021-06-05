package com.screenname_example.controller;

import com.screenname_example.form.JwtRequest;
import com.screenname_example.form.JwtResponse;
import com.screenname_example.service.AccountService;
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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {

        String token = accountService
                .checkLogin(authenticationRequest);
        if(token != null){
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui.html");
    }
}
