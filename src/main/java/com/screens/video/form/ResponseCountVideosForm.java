package com.screens.video.form;



import java.io.Serializable;
import java.util.List;

public class ResponseCountVideosForm implements Serializable {
    private int totalOfRecord;
    private List<StoreCountResponseSupporter> stores;

    public ResponseCountVideosForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<StoreCountResponseSupporter> getStores() {
        return stores;
    }

    public void setStores(List<StoreCountResponseSupporter> stores) {
        this.stores = stores;
    }
}
