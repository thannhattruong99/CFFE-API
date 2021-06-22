package com.listeners.events;

import com.screens.file.dto.VideoProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    //TruongNT
    public void publishEvent(String eventId, String relativeFilePath) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventId, relativeFilePath));
    }

    //LuanNM
    public void publishEvent(String eventId, List<VideoProperty> videoPropertyList) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventId, videoPropertyList));
    }

}
