package com.spring.training.messaging;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Sender {

    final KafkaTemplate<String, Object> template;

    public void send(Message message) {
        template.send(ApplicationConfig.KAFKA_TOPIC, message);
    }

}
