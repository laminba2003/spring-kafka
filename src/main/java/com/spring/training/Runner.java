package com.spring.training;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Runner implements CommandLineRunner {

    private final Sender sender;
    private final Receiver receiver;

    @Override
    public void run(String... args) throws Exception {
        Message message = new Message("laminba2003@gmail.com","moussa@gmail.com","this is a test");
        sender.send(message);
    }

}
