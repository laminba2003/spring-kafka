package com.spring.training.config;

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
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class ApplicationConfig {

    public static final String KAFKA_TOPIC = "messages";

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.afterPropertiesSet();
        factory.setCommonErrorHandler(errorHandler());
        configurer.configure(factory, consumerFactory);
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
            if (exception.getCause() instanceof DeserializationException) {
                DeserializationException deserializationException = (DeserializationException) exception.getCause();
                log.error("cannot deserialize message with payload : {} ", new String(deserializationException.getData()));
            } else {
                log.error("error : {} while consuming message with payload : {}", exception.getMessage(), consumerRecord.value());
            }
        }, new FixedBackOff(10000L, 10));
        errorHandler.addNotRetryableExceptions(NullPointerException.class, DeserializationException.class);
        return errorHandler;
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