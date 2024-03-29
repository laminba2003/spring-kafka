package com.spring.training.messaging;

import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Runner implements CommandLineRunner {

    final Producer producer;

    @Override
    public void run(String... args) {
        Message message = new Message("laminba2003@gmail.com", "moussa@gmail.com", "this is a test");
        producer.produce(message.getFrom(), message);
    }

}
