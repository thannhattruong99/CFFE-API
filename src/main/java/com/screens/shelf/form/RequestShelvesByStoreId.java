package com.screens.shelf.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestShelvesByStoreId implements Serializable {
    @NotEmpty(message = "MSG-069")
    private String storeId;

    public RequestShelvesByStoreId() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
