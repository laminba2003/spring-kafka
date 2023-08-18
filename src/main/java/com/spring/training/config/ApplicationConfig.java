package com.spring.training.config;

import com.spring.training.messaging.Processor;
import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@AllArgsConstructor
public class ApplicationConfig {

    public static final String KAFKA_TOPIC = "messages";

    @Bean
    public KafkaListenerErrorHandler validationErrorHandler() {
        return (message, exception) -> {
            log.error("validation error occurred with {} ", message);
            return message;
        };
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory);
        factory.setCommonErrorHandler(new CommonErrorHandler() {
            @Override
            public void handleRecord(Exception exception, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
                if (exception.getCause() instanceof DeserializationException) {
                    DeserializationException deserializationException = (DeserializationException) exception.getCause();
                    log.error("cannot deserialize message with payload : {} ", new String(deserializationException.getData()));
                }
            }
        });
        return factory;
    }

    @Bean
    public StreamProperties streamProperties() {
        return new StreamProperties();
    }

    @Bean
    public KafkaStreamsConfiguration kStreamsConfig() {
        Map<String, Object> configs = new HashMap<>();
        StreamProperties streamProperties = streamProperties();
        streamProperties.getProperties().forEach((key, value) -> configs.put(key.toString(), value));
        return new KafkaStreamsConfiguration(configs);
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