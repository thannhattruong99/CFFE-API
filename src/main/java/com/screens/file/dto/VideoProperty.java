package com.screens.file.dto;

import com.screens.file.listener.detector.EmotionDTO;

import java.io.Serializable;

public class VideoProperty implements Serializable {
    private String videoId;
    private String videoNameOriginal;
    private String videoNameUUID;
    private String videoUrl;
    private String startedTime;
    private String endedTime;
    private int duration;
    private String cameraId;
    private String macAddress;
    private String shelfCameraMappingId;
    private String stackProductCameraMappingId;
    private int statusId;
    private int totalPerson;
    private int typeVideo;
    private EmotionDTO emotions;

    public VideoProperty() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public EmotionDTO getEmotions() {
        return emotions;
    }

    public void setEmotions(EmotionDTO emotions) {
        this.emotions = emotions;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public int getTypeVideo() {
        return typeVideo;
    }

    public void setTypeVideo(int typeVideo) {
        this.typeVideo = typeVideo;
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    public String getVideoNameOriginal() {
        return videoNameOriginal;
    }

    public void setVideoNameOriginal(String videoNameOriginal) {
        this.videoNameOriginal = videoNameOriginal;
    }

    public int getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(int totalPerson) {
        this.totalPerson = totalPerson;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoNameUUID() {
        return videoNameUUID;
    }

    public void setVideoNameUUID(String videoNameUUID) {
        this.videoNameUUID = videoNameUUID;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getShelfCameraMappingId() {
        return shelfCameraMappingId;
    }

    public void setShelfCameraMappingId(String shelfCameraMappingId) {
        this.shelfCameraMappingId = shelfCameraMappingId;
    }

    public String getStackProductCameraMappingId() {
        return stackProductCameraMappingId;
    }

    public void setStackProductCameraMappingId(String stackProductCameraMappingId) {
        this.stackProductCameraMappingId = stackProductCameraMappingId;
    }
}
