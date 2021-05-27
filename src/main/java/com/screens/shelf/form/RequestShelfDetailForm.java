package com.screens.shelf.form;

import javax.validation.constraints.NotEmpty;

public class RequestShelfDetailForm {
    @NotEmpty(message = "MSG-071")
    private String shelfId;

    public RequestShelfDetailForm() {
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }
}