package com.spring.training;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import javax.validation.Valid;

@Service
@Slf4j
public class Receiver {

    @KafkaListener(topics = ApplicationConfig.kafkaTopic)
    public void consume(@Payload @Valid Message message) {
        log.info("message received  : " + message);
    }

}