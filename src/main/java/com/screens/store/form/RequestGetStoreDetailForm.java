package com.screens.store.form;

import com.common.form.RequestGetBaseForm;

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
