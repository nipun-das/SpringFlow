package com.nipundas.notification_service.service;

import com.nipundas.notification_service.order.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    @KafkaListener(topics="order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got message from order-placed kafkaTopic ",orderPlacedEvent);

        //sending mail


    }
}
