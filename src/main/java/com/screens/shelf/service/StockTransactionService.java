package com.screens.shelf.service;

import com.listeners.events.EventPublisher;
import com.screens.shelf.dto.Notification;
import com.screens.shelf.dto.StockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

@Service
public class StockTransactionService {
    @Autowired
    EventPublisher eventPublisher;

    public List<Notification> notificationList = new ArrayList<>();
    List<String> stockNames = Arrays.asList("Vietcombank,VietinBank,BIDV,PVCombank".split(","));


//    public void init(){
//        String uuid = UUID.randomUUID().toString();
//        eventPublisher.publishEvent("Upload file", uuid);
//    }

    public Flux<StockTransaction> getStockTransactions() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
        if(notificationList.size() <= 0){
            createRandomStock();
            notificationList.forEach(System.out::println);
        }
        interval.subscribe((i) -> notificationList.forEach(notification -> notification.setStatusId(1)));

        Flux<StockTransaction> stockTransactionFlux = Flux.fromStream(Stream.generate(() -> new StockTransaction(getRandomUser(), getRandomStock(), new Date())));
        return Flux.zip(interval, stockTransactionFlux).map(Tuple2::getT2);
    }

    void createRandomStock() {
        stockNames.forEach(stockName -> {
            notificationList.add(new Notification(stockName, 1));
        });
    }


    String getRandomUser() {
        String users[] = "TamNT,TamCT,HieuPV,HieuTM,ThanhD".split(",");
        return users[new Random().nextInt(users.length)];
    }

    Notification getRandomStock() {
        return notificationList.get(new Random().nextInt(notificationList.size()));
    }

}
