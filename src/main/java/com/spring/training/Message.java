package com.spring.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @NotBlank
    String from;
    @NotBlank
    String to;
    @NotBlank
    String content;
}
