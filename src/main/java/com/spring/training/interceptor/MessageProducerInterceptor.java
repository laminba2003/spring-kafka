package com.spring.training.interceptor;

import com.spring.training.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class MessageProducerInterceptor implements ProducerInterceptor<String, Message> {

    @Override
    public ProducerRecord<String, Message> onSend(ProducerRecord<String, Message> producerRecord) {
        log.info("sending message to topic : {} with payload : {} ", producerRecord.topic(), producerRecord.value());
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        log.info("message sent to topic : {} and partition : {} at offset : {}", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
