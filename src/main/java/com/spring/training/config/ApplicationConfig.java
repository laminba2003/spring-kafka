package com.spring.training.config;

import com.spring.training.model.Message;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.HashMap;
import java.util.Map;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@Slf4j
@AllArgsConstructor
public class ApplicationConfig implements KafkaListenerConfigurer {

    public static final String KAFKA_TOPIC = "messages";

    final LocalValidatorFactoryBean validator;

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }

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
        Map<String, Object> props = new HashMap<>();
        StreamProperties streamProperties = streamProperties();
        props.put(APPLICATION_ID_CONFIG, streamProperties.get(APPLICATION_ID_CONFIG));
        props.put(BOOTSTRAP_SERVERS_CONFIG, streamProperties.get(BOOTSTRAP_SERVERS_CONFIG));
        props.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(SCHEMA_REGISTRY_URL_CONFIG, streamProperties.get(SCHEMA_REGISTRY_URL_CONFIG));
        props.put(NUM_STREAM_THREADS_CONFIG, streamProperties.get(NUM_STREAM_THREADS_CONFIG));
        props.put(REPLICATION_FACTOR_CONFIG, streamProperties.get(REPLICATION_FACTOR_CONFIG));
        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public StreamsBuilderFactoryBean streamBuilderFactoryBean() {
        return new StreamsBuilderFactoryBean(kStreamsConfig());
    }

    @Bean
    public KStream<String, Message> processMessages(StreamsBuilder builder) {
        KStream<String, Message> source = builder.stream(ApplicationConfig.KAFKA_TOPIC);
        source.map((key, value) -> KeyValue.pair(value.getFrom(), value.getContent()))
                .to("process-messages", Produced.with(Serdes.String(), Serdes.String()));
        return source;
    }
}