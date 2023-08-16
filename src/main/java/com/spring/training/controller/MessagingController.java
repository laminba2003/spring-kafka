package com.spring.training.controller;

import com.spring.training.dto.MessageDto;
import com.spring.training.messaging.Sender;
import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/messaging")
@AllArgsConstructor
public class MessagingController {

    final Sender sender;


    @PostMapping
    public void sendMessage(@RequestBody @Valid MessageDto messageDto) {
        Message message = Message.newBuilder()
                .setFrom(messageDto.getFrom())
                .setTo(messageDto.getTo())
                .setContent(messageDto.getContent()).build();
        sender.send(message);
    }

}
