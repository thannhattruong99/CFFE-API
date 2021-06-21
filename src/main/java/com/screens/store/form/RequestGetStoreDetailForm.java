package com.screens.store.form;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class RequestGetStoreDetailForm implements Serializable {
    @NotEmpty(message = "MSG-069")
    private String storeId;

    public RequestGetStoreDetailForm() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
