package com.screens.store.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class RequestUpdateAnalyzedTime implements Serializable {
    @NotEmpty(message = "MSG-069")
    private String storeId;

    @NotEmpty(message = "MSG-070")
    @Pattern(regexp = "([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])", message = "MSG-072")
    private String analyzedTime;

    public RequestUpdateAnalyzedTime() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getAnalyzedTime() {
        return analyzedTime;
    }

    public void setAnalyzedTime(String analyzedTime) {
        this.analyzedTime = analyzedTime;
    }
}
