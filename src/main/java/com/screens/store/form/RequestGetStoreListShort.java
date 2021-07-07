package com.screens.store.form;

import java.io.Serializable;

public class RequestGetStoreListShort implements Serializable {

    private boolean all;

    public RequestGetStoreListShort() {
    }

    public boolean getAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }
}
