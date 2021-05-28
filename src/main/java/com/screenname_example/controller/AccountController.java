package com.screenname_example.controller;

import com.util.ResponseSupporter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AccountController {

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
}
