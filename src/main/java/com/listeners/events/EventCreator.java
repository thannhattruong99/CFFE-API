package com.listeners.events;

import org.springframework.context.ApplicationEvent;

public class EventCreator extends ApplicationEvent {
    private String eventId;
    private String eventName;
    private String relativeFilePath;
    private int status;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public EventCreator(Object source, String eventName, String relativeFilePath) {
        super(source);
        this.eventName = eventName;
        this.relativeFilePath = relativeFilePath;
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
