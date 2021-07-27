package com.screens.video.form;

import java.io.Serializable;

public class HotSpotResponseSupporter implements Serializable {
    private String hotspotId;
    private String startedTime;
    private String endedTime;
    private int TotalPeople;
    private String description;
    private String createdTime;
    private String updatedTime;

    public HotSpotResponseSupporter() {
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    public int getTotalPeople() {
        return TotalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        TotalPeople = totalPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
