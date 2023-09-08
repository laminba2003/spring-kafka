package com.spring.training.messaging;

import com.spring.training.config.ApplicationConfig;
import com.spring.training.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class Consumer {

    @KafkaListener(topics = ApplicationConfig.KAFKA_TOPIC)
    public void consume(ConsumerRecord<String, Message> record) {
        log.info("message consumed from topic : {} and partition : {} at offset : {} with key : {}, payload : {} and timestamp : {}"
                , record.topic(), record.partition(), record.offset(), record.key(), record.value(), new Date(record.timestamp()));
    }

}