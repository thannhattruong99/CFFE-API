package com.screens.video.form;

import java.util.List;

public class StoreEmotionResponseSupporter {
    private String storeId;
    private String storeName;
    private List<VideoResponseSupporter> videos;

    public StoreEmotionResponseSupporter() {
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<VideoResponseSupporter> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoResponseSupporter> videos) {
        this.videos = videos;
    }
}
