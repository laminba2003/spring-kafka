package com.spring.training;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@Slf4j
@AllArgsConstructor
public class ApplicationConfig implements KafkaListenerConfigurer {

    static final String KAFKA_TOPIC = "javainuse";

    LocalValidatorFactoryBean validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }

    @Bean
    public KafkaListenerErrorHandler validationErrorHandler() {
        return (message, exception) -> {
            log.error("validation occured with {} ", message);
            return message;
        };
    }

}