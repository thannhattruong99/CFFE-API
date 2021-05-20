package com.screens.manager.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class RequestManagerDetailForm implements Serializable {
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
