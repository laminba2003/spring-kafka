package com.spring.training.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties(prefix = "spring.kafka.streams")
@Data
public class StreamProperties {

    private final Properties properties = new Properties();

    public Object get(String key) {
        return properties.get(key);
    }

}
