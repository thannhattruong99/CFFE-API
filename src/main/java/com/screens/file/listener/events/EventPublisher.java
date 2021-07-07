package com.screens.file.listener.events;

import com.screens.file.dto.VideoProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventPublisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    //LuanNM
    public void publishEvent(String eventId, List<VideoProperty> videoPropertyList) {
        applicationEventPublisher.publishEvent(new EventCreator(this, eventId, videoPropertyList));
    }

}
