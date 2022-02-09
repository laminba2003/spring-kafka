package com.spring.training;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Sender {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(Message message) {
        kafkaTemplate.send(Application.kafkaTopic, message);
    }

}
