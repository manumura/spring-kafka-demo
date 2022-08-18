package com.demo.kafka.message;

import com.demo.kafka.constants.Constants;
import com.demo.kafka.dto.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Customer> kafkaTemplate;

    public void sendMessage(Long key, Customer payload) {
        log.info("Sending message with key {} and payload {}", key, payload);

        Message<Customer> message = MessageBuilder
                .withPayload(payload)
                .setHeader(KafkaHeaders.TOPIC, Constants.TOPIC)
                .setHeader(KafkaHeaders.MESSAGE_KEY, String.valueOf(key))
                .build();

        // Test batch processing
        for (int i = 0; i < 10; i++) {
            ListenableFuture<SendResult<String, Customer>> result = kafkaTemplate.send(message);
            result.addCallback(
                    r -> {
                        assert r != null;
                       log.info("Sent message=[{}] with offset=[{}]", message, r.getRecordMetadata().offset());
                    },
                    ex ->log.info("Unable to send message=[{}] due to : {}", message, ex.getMessage())
            );
        }
    }
}
