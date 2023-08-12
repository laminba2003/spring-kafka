package com.spring.training.messaging;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Receiver {

    @KafkaListener(topics = ApplicationConfig.KAFKA_TOPIC, errorHandler = "validationErrorHandler")
    public void consume(@Payload Message message) {
        log.info("message received  : {} ", message);
    }

}