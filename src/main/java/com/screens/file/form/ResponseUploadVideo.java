package com.screens.file.form;

import com.screens.file.dto.VideoProperty;

import java.io.Serializable;
import java.util.List;

public class ResponseUploadVideo implements Serializable {
    private List<String> filePath;

    private List<VideoProperty> videoPropertyList;

    private String idEvent;

    private List<String> errorCodes;

    public ResponseUploadVideo() {
    }

    public List<VideoProperty> getVideoPropertyList() {
        return videoPropertyList;
    }

    public void setVideoPropertyList(List<VideoProperty> videoPropertyList) {
        this.videoPropertyList = videoPropertyList;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
