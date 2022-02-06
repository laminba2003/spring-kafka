package com.spring.training;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {

    @KafkaListener(topics = Application.kafkaTopic)
    public void receiveMessage(Message message) {
        System.out.println("message received...");
        System.out.println("message from "+message.getFrom());
        System.out.println("message to "+message.getTo());
        System.out.println("message content "+message.getContent());
    }

}
