package com.screens.file.service;

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

    public List<Notification> notificationList = new ArrayList<>();

    // chua hieu cho nay
//    public void init(){
//        String uuid = UUID.randomUUID().toString();
//        eventPublisher.publishEvent("Upload file", uuid);
//    }

    public Flux<FileTransaction> getFileTransactions() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        if(notificationList.size() <= 0){
            createRandomNotification();
        }
        interval.subscribe((i) -> notificationList.forEach(
                notification -> notification.setPrice(notification.getPrice())
        ));
        Flux<FileTransaction> fileTransactionFlux = Flux.fromStream(
                Stream.generate(() -> new FileTransaction(getRandomUser(), getRandomNotification(), new Date()))
        );
        return Flux.zip(interval, fileTransactionFlux).map(Tuple2::getT2);
    }

    void createRandomNotification() {
        List<String> notificationName = Arrays.asList("Vietcombank,VietinBank,BIDV,PVCombank".split(","));
        notificationName.forEach(item -> {
            notificationList.add(new Notification(item, generateRandomPrice()));
        });
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

    Notification getRandomNotification() {
        return notificationList.get(new Random().nextInt(notificationList.size()));
    }

}
