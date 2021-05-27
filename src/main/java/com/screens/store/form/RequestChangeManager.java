package com.screens.store.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RequestChangeManager implements Serializable {
    @NotEmpty(message = "MSG-069")
    private String storeId;

    @NotEmpty(message = "MSG-001")
    private String userId;

    @NotNull
    private int active;

    public RequestChangeManager() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
