package com.spring.training.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MessageDto {
    @NotBlank
    @Email
    String from;
    @NotBlank
    @Email
    String to;
    @NotBlank
    String content;
}
