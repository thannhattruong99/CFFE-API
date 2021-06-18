package com.screens.video.form;

import com.screens.store.form.StoreResponseSupporter;

import java.util.List;

public class ResponseEmotionVideosForm {
    private int totalOfRecord;
    private List<StoreEmotionResponseSupporter> stores;

    public ResponseEmotionVideosForm() {
    }

    public int getTotalOfRecord() {
        return totalOfRecord;
    }

    public void setTotalOfRecord(int totalOfRecord) {
        this.totalOfRecord = totalOfRecord;
    }

    public List<StoreEmotionResponseSupporter> getStores() {
        return stores;
    }

    public void setStores(List<StoreEmotionResponseSupporter> stores) {
        this.stores = stores;
    }
}
