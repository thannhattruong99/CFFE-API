package com.screens.file.service;

import com.listeners.events.CustomEventListener;
import com.listeners.events.EventCreator;
import com.listeners.events.EventPublisher;
import com.screens.file.dto.FileTransaction;
import com.screens.file.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.io.Serializable;
import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FileTransactionService implements Serializable {
    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    CustomEventListener customEventListener;

    public Flux<FileTransaction> getFileTransactions(String eventId) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));
        // Lay data moi
        EventCreator data = customEventListener.getEventCreatorMap().get(eventId);

        Flux<FileTransaction> fileTransactionFlux = Flux.fromStream(
                // generate new data.
                Stream.generate(() -> new FileTransaction(getRandomUser(),
                        new Notification(data.getEventName(), data.getStatus()),
                        new Date()))
        );

        if (data.getStatus() == 99) {
            Map<String, EventCreator> eventCreatorMap = customEventListener.getEventCreatorMap();
            eventCreatorMap.remove("test");
            customEventListener.setEventCreatorMap(eventCreatorMap);
        }
        return Flux.zip(interval, fileTransactionFlux).map(Tuple2::getT2);
    }



    int generateRandomPrice() {
        int max = 100;
        int min = 1;
        return (int) ((Math.random() * (max - min)) + min);
    }

    String getRandomUser() {
        String users[] = "HieuHd,LuanNM,TruongNT,HuuDN".split(",");
        return users[new Random().nextInt(users.length)];
    }



}
