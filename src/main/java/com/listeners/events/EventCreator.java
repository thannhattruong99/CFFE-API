package com.listeners.events;

import com.screens.file.dto.VideoProperty;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class EventCreator extends ApplicationEvent {
    private String eventId;
    private String eventName;
    private String relativeFilePath;
    private List<VideoProperty> videoPropertyList;
    private String message;
    private int status;


    public EventCreator(Object source, String eventName, String relativeFilePath) {
        super(source);
        this.eventName = eventName;
        this.relativeFilePath = relativeFilePath;
    }

    //LuanNM
    public EventCreator(Object source,String eventId, List<VideoProperty> videoPropertyList) {
        super(source);
        this.eventId = eventId;
        this.videoPropertyList = videoPropertyList;
    }

    public EventCreator(Object source, String eventId) {
        super(source);
        this.eventId = eventId;
    }

    public EventCreator(Object source, String eventId, String eventName, String relativeFilePath) {
        super(source);
        this.eventId = eventId;
        this.eventName = eventName;
        this.relativeFilePath = relativeFilePath;
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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }

    public void setRelativeFilePath(String relativeFilePath) {
        this.relativeFilePath = relativeFilePath;
    }
}
