package com.screenname.controller;

import com.common.dto.Account;
import com.screenname.form.AccountForm;
import com.screenname.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/getAllAccount2")
    public List<Account> sayHello() {
        return accountService.getStudentAll();
    }

    @GetMapping("/getAllAccountDemo2")
    public List<Account> sayHelloDemo() {
        return accountService.getStudentAll();
    }

    @GetMapping("")
    public RedirectView init(){
        return new RedirectView("/swagger-ui-custom.html");
    }

    //    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    @RequestMapping(value = "/createAnAccount", method = RequestMethod.POST)
    public String createAnAccount(Model model, //
                                  @ModelAttribute("account") @Valid AccountForm account, //
                                  BindingResult result) {
        if (result.hasErrors()) {
            for (Object object : result.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    System.out.println(fieldError.getCode());
                }

                if (object instanceof ObjectError) {
                    ObjectError objectError = (ObjectError) object;

                    System.out.println(objectError.getCode());
                }
            }
            return "error";
        }

        return "Ngon";
    }
}