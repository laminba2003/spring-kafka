package com.spring.training;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = Application.kafkaTopic)
@Slf4j
public class Receiver {

    @KafkaHandler
    public void consume(@Payload String input, @Header(KafkaHeaders.OFFSET) String offset){
        log.info(" offset: " + offset + "Incoming info: "+input);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(input, Message.class);
            log.info("Message  :"+message);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

}