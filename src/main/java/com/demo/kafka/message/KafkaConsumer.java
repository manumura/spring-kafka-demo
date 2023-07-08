package com.demo.kafka.message;

import com.demo.kafka.constants.Constants;
import com.demo.kafka.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KafkaConsumer {
    // https://github.com/evgeniy-khist/spring-kafka-non-blocking-retries-and-dlt
//    @RetryableTopic(
//            attempts = "4",
//            backoff = @Backoff(delay = 1000, multiplier = 2.0),
//            autoCreateTopics = "false",
//            dltTopicSuffix = "-dlt",
//            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
//    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID)
//    public void onMessage(@Payload Customer customer, @Headers MessageHeaders headers) {
//        log.info("----- Received Message in group {}: {} -----", Constants.GROUP_ID, customer);
//        log.info("Headers: {}", headers);
//
//        // test DLT : comment in KafkaConsumerConfig factory.setBatchListener(true);
//        throw new RuntimeException("test DLT");
//    }

    // Test batch consume props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID)
    public void onMessage(@Payload List<Customer> customers,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                          @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("----- Starting the process to receive batch messages in topic {} -----", topic);

        for (int i = 0; i < customers.size(); i++) {
            log.info("received message='{}' with partition-offset='{}'",
                    customers.get(i).toString(), partitions.get(i) + "-" + offsets.get(i));
        }
        log.info("all the batch messages are consumed");
    }

    @DltHandler
    public void dlt(String in, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("----- DLT HANDLER -----");
        log.info("{} from {}", in, topic);
    }
}
