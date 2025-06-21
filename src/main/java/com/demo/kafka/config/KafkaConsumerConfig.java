package com.demo.kafka.config;

import com.demo.kafka.constants.Constants;
import com.demo.kafka.dto.Customer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

//    @Value("${spring.kafka.listener.authExceptionRetryInterval}")
//    private long authExceptionRetryInterval;

    private Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Avoid poison pill issues
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.GROUP_ID);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        // Consume messages by batch
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
        return props;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Customer> customerKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(Customer.class))
        );
        factory.setConsumerFactory(consumerFactory);
//        factory.setRecordMessageConverter(new StringJsonMessageConverter());
//        factory.setBatchListener(true);
//        factory.setContainerCustomizer(
//                container -> container.getContainerProperties()
//                        .setAuthExceptionRetryInterval(Duration.ofMillis(authExceptionRetryInterval)));
        factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
        return factory;
    }
}
