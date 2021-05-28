package com.screens.stack.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetStackDetailForm implements Serializable {
    @NotEmpty(message = "MSG-078")
    private String stackId;

    public RequestGetStackDetailForm() {
    }

    public String getStackId() {
        return stackId;
    }

    public void setStackId(String stackId) {
        this.stackId = stackId;
    }
}
