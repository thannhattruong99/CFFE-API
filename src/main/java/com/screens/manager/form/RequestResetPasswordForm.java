package com.screens.manager.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestResetPasswordForm {
    @NotEmpty(message = "MSG-001")
    @Size(min = 1, max = 30, message = "MSG-009")
    private String userName;
    @Nullable @Email(message = "MSG-045")
    private String email;

    public RequestResetPasswordForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
