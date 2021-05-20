package com.screens.manager.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RequestManagerDetailForm {
    @NotEmpty(message = "MSG-041")
    @Size(max = 30, message = "MSG-041")
    private String userName;

    public RequestManagerDetailForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
