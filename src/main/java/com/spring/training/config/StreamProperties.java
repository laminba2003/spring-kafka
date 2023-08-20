package com.spring.training.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@ConfigurationProperties(prefix = "spring.kafka.streams")
@Data
public class StreamProperties {

    private final Properties properties = new Properties();

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        properties.forEach((key, value) -> map.put(key.toString(), value));
        return map;
    }

}
