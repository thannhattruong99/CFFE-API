package com.screens.video.form;

import java.util.List;

public class ShelfVideoResponseSupporter {
    private String shelfId;
    private String shelfName;
    private List<VideoResponseSupporter> videos;

    public ShelfVideoResponseSupporter() {
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public String getShelfId() {
        return shelfId;
    }

    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }

    public List<VideoResponseSupporter> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoResponseSupporter> videos) {
        this.videos = videos;
    }
}
