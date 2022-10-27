package com.spring.training;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;
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

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory);
        factory.setCommonErrorHandler(new CommonErrorHandler() {
            @Override
            public void handleRecord(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
                if (exception.getCause() != null && exception.getCause() instanceof DeserializationException) {
                    DeserializationException deserializationException = (DeserializationException) exception.getCause();
                    log.error("cannot deserialize messsage with payload : {} ", new String(deserializationException.getData()));
                }
            }
        });
        return factory;
    }

}