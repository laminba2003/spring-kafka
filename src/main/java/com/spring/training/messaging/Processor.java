package com.spring.training.messaging;

import com.spring.training.model.Message;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.stereotype.Service;

import static com.spring.training.config.ApplicationConfig.KAFKA_TOPIC;

@Service
@AllArgsConstructor
public class Processor {

    final StreamsBuilder builder;

    public KStream<String, Message> processMessages() {
        KStream<String, Message> source = builder.stream(KAFKA_TOPIC);
        source.map((key, value) -> KeyValue.pair(value.getFrom(), value.getContent()))
                .to("process-messages", Produced.with(Serdes.String(), Serdes.String()));
        return source;
    }
}
