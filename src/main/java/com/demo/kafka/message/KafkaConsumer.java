package com.demo.kafka.message;

import com.demo.kafka.constants.Constants;
import com.demo.kafka.dto.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class KafkaConsumer {

//    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID)
//    public void onMessage(@Payload Customer customer, @Headers MessageHeaders headers) {
//        log.info("Received Message in group {}: {}", Constants.GROUP_ID, customer);
//        log.info("Headers: {}", headers);
//    }

    // Test batch consume props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
    @KafkaListener(topics = Constants.TOPIC, groupId = Constants.GROUP_ID)
    public void onMessage(@Payload List<Customer> customers,
                          @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                          @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("Starting the process to receive batch messages");
        for (int i = 0; i < customers.size(); i++) {
            log.info("received message='{}' with partition-offset='{}'",
                    customers.get(i).toString(), partitions.get(i) + "-" + offsets.get(i));
        }
        log.info("all the batch messages are consumed");
    }
}
