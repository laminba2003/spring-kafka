package com.spring.training.interceptor;

import com.spring.training.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;

@Slf4j
public class MessageConsumerInterceptor implements ConsumerInterceptor<String, Message> {

    @Override
    public ConsumerRecords<String, Message> onConsume(ConsumerRecords<String, Message> consumerRecords) {
        log.info("{} record(s) received from broker", consumerRecords.count());
        return consumerRecords;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
