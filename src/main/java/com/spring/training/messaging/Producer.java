package com.spring.training.messaging;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Producer {

    final KafkaTemplate<String, Message> template;

    public void produce(String key, Message message) {
        ProducerRecord<String, Message> record = new ProducerRecord<>(ApplicationConfig.KAFKA_TOPIC, key, message);
        template.send(record);
    }

    public void produce(Message message) {
        produce(null, message);
    }

}
