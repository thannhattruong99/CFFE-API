package com.screens.file.listener.events;

import com.screens.file.dto.VideoProperty;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class EventCreator extends ApplicationEvent {
    private String eventId;
    private List<VideoProperty> videoPropertyList;
    private String message;
    private int status;
    private int totalFile;
    private int numberFileDone;
    private List<String> fileSuccess;
    private List<String> fileError;

    public EventCreator(Object source,String eventId, List<VideoProperty> videoPropertyList) {
        super(source);
        this.eventId = eventId;
        this.videoPropertyList = videoPropertyList;
    }

    public int getTotalFile() {
        return totalFile;
    }

    public void setTotalFile(int totalFile) {
        this.totalFile = totalFile;
    }

    public int getNumberFileDone() {
        return numberFileDone;
    }

    public void setNumberFileDone(int numberFileDone) {
        this.numberFileDone = numberFileDone;
    }

    public List<String> getFileSuccess() {
        return fileSuccess;
    }

    public void setFileSuccess(List<String> fileSuccess) {
        this.fileSuccess = fileSuccess;
    }

    public List<String> getFileError() {
        return fileError;
    }

    public void setFileError(List<String> fileError) {
        this.fileError = fileError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<VideoProperty> getVideoPropertyList() {
        return videoPropertyList;
    }

    public void setVideoPropertyList(List<VideoProperty> videoPropertyList) {
        this.videoPropertyList = videoPropertyList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

}
