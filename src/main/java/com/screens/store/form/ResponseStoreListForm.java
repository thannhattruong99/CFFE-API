package com.screens.store.form;


import java.io.Serializable;
import java.util.List;

public class ResponseStoreListForm implements Serializable {
    private int totalOfRecord;
    private List<StoreResponseSupporter> stores;

    public ResponseStoreListForm() {
    }

    public List<StoreResponseSupporter> getStores() {
        return stores;
    }

    public void setStores(List<StoreResponseSupporter> stores) {
        this.stores = stores;
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }
}
