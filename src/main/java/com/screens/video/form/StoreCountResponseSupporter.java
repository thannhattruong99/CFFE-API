package com.screens.video.form;

import java.util.List;

public class StoreCountResponseSupporter {
    private String storeId;
    private String storeName;
    private List<ShelfVideoResponseSupporter> shelves;

    public StoreCountResponseSupporter() {
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<ShelfVideoResponseSupporter> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelfVideoResponseSupporter> shelves) {
        this.shelves = shelves;
    }
}
