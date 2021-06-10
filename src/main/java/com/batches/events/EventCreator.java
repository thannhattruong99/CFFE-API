package com.batches.events;

import org.springframework.context.ApplicationEvent;

public class EventCreator extends ApplicationEvent {
    private String eventName;
    private String relativeFilePath;
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

    public String getEventName() {
        return eventName;
    }

    public String getRelativeFilePath() {
        return relativeFilePath;
    }
}
