package com.spring.training.messaging;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;

@Slf4j
public class ErrorHandler implements CommonErrorHandler  {
    @Override
    public void handleRecord(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        if (exception.getCause() instanceof DeserializationException) {
            DeserializationException deserializationException = (DeserializationException) exception.getCause();
            log.error("cannot deserialize message with payload : {} ", new String(deserializationException.getData()));
        }
    }

}