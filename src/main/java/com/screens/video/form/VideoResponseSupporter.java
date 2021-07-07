package com.screens.video.form;

public class VideoResponseSupporter {
    private String videoId;
    private String videoName;
    private String videoUrl;
    private String startedTime;
    private String endedTime;
    private int statusId;
    private EmotionResponseSupporter emotion;
    private HotSpotResponseSupporter hotSpot;

    public VideoResponseSupporter() {
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(String startedTime) {
        this.startedTime = startedTime;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public EmotionResponseSupporter getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionResponseSupporter emotion) {
        this.emotion = emotion;
    }

    public HotSpotResponseSupporter getHotSpot() {
        return hotSpot;
    }

    public void setHotSpot(HotSpotResponseSupporter hotSpot) {
        this.hotSpot = hotSpot;
    }
}
