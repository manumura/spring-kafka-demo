package com.demo.kafka.config;

import com.demo.kafka.dto.Customer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        return props;
    }

    @Bean
    public KafkaTemplate<String, Customer> kafkaTemplate() {
        var producerFactory = new DefaultKafkaProducerFactory<String, Customer>(producerConfigs());
        return new KafkaTemplate<>(producerFactory);
    }
}
