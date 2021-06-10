package com.batches.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String eventName, String relativeFilePath) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventName, relativeFilePath));
    }

}
