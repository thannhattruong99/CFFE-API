package com.listeners.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String eventId, String relativeFilePath) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventId, relativeFilePath));
    }

    public void publishEvent(String eventId) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventId));
    }

}
