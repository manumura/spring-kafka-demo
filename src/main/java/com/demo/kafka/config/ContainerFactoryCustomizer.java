package com.demo.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.AbstractKafkaListenerContainerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ContainerFactoryCustomizer {

    @Value("${spring.kafka.listener.authExceptionRetryInterval}")
    private long authExceptionRetryInterval;

    ContainerFactoryCustomizer(AbstractKafkaListenerContainerFactory<?, ?, ?> factory) {
        factory.setContainerCustomizer(
                container -> container.getContainerProperties()
                        .setAuthExceptionRetryInterval(Duration.ofMillis(authExceptionRetryInterval)));
    }
}
