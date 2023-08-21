package com.spring.training.config;

import com.spring.training.messaging.ErrorHandler;
import com.spring.training.messaging.Processor;
import com.spring.training.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;

@Configuration
@Slf4j
public class ApplicationConfig {

    public static final String KAFKA_TOPIC = "messages";

    @Bean
    public KafkaListenerErrorHandler validationErrorHandler() {
        return (message, exception) -> {
            log.error("validation error occurred with payload : {} and error message : {} ", message, exception.getMessage());
            return message;
        };
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setCommonErrorHandler(new ErrorHandler());
        configurer.configure(factory, consumerFactory);
        return factory;
    }

    @Bean
    public StreamProperties streamProperties() {
        return new StreamProperties();
    }

    @Bean
    public KafkaStreamsConfiguration kStreamsConfig() {
        return new KafkaStreamsConfiguration(streamProperties().toMap());
    }

    @Bean
    public StreamsBuilderFactoryBean streamBuilderFactoryBean() {
        return new StreamsBuilderFactoryBean(kStreamsConfig());
    }

    @Bean
    public KStream<String, Message> processMessages(Processor processor) {
        return processor.processMessages();
    }

}