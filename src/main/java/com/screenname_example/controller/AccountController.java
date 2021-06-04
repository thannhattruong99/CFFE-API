package com.screenname_example.controller;

import com.common.config.JwtTokenHelper;
import com.screenname_example.dto.AccountDTO;
import com.screenname_example.form.JwtRequest;
import com.screenname_example.form.JwtResponse;
import com.screenname_example.service.AccountService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AccountController {

    private static final String E001 = "E001";
    private static final String E000 = "E000";

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        AccountDTO result = accountService
                .checkLogin(authenticationRequest);
        if(result != null){
            final String token = jwtTokenHelper.generateToken(result);
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui-custom.html");
    }
}
