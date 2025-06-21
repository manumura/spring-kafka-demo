package com.demo.kafka.message;

import com.demo.kafka.constants.Constants;
import com.demo.kafka.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.RetryTopicHeaders;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    // https://stackoverflow.com/questions/76810770/spring-kafka-non-blocking-how-to-get-the-retry-attempt-count
    // https://github.com/evgeniy-khist/spring-kafka-non-blocking-retries-and-dlt
    // { "id": 1, "name": "Bob", "age": 40 }
    // { "id": 2, "name": "error", "age": 40 }
    @RetryableTopic(
            attempts = "5",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE
    )
    @KafkaListener(id = "customer-consumer",
            topics = Constants.TOPIC,
            groupId = Constants.GROUP_ID,
           containerFactory = "customerKafkaListenerContainerFactory")
    public void onMessage(@Payload Customer customer,
//                          @Headers MessageHeaders headers,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer attempt) {
//        log.info("Headers: {}", headers);
        log.info("----- Received Message in topic {} with groupId {} on attempt {}: {} -----", topic, Constants.GROUP_ID, attempt, customer);

        // test DLT : comment in KafkaConsumerConfig factory.setBatchListener(true);
        if (customer.name().equals("error")) {
            throw new RuntimeException("Simulated failure for testing retry and DLT");
        }
    }

    // Test batch consume props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
//    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID)
//    public void onMessage(@Payload List<Customer> customers,
//                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
//                          @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
//                          @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
//        log.info("----- Starting the process to receive batch messages in topic {} -----", topic);
//
//        for (int i = 0; i < customers.size(); i++) {
//            log.info("received message='{}' with partition-offset='{}'",
//                    customers.get(i).toString(), partitions.get(i) + "-" + offsets.get(i));
//        }
//
//        log.info("----- All the batch messages are consumed -----");
//    }

    @DltHandler
    public void dlt(@Payload Customer customer,
                    @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                    @Header(KafkaHeaders.GROUP_ID) String groupId,
                    @Header(name = RetryTopicHeaders.DEFAULT_HEADER_ATTEMPTS, required = false) Integer attempt) {
        log.info("----- DLT HANDLER -----");
        log.info("----- {} from topic {} with groupId {} on attempt {} -----", customer, topic, groupId, attempt);
    }
}
