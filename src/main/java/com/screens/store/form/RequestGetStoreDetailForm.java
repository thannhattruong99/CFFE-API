package com.screens.store.form;

import java.io.Serializable;

public class RequestGetStoreDetailForm implements Serializable {
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
