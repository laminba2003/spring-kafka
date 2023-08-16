package com.spring.training.messaging;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Sender {

    final KafkaTemplate<String, Message> template;

    public void send(Message message) {
        ProducerRecord<String, Message> record = new ProducerRecord<>(ApplicationConfig.KAFKA_TOPIC, message.getFrom(), message);
        template.send(record);
    }

}
