package com.demo.kafka.controller;

import com.demo.kafka.dto.Customer;
import com.demo.kafka.message.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {

    private final KafkaProducer kafkaProducer;

    @GetMapping(value = "/api/message/send") // APPLICATION_NDJSON_VALUE
    public ResponseEntity<Void> sendMessage(@Valid @RequestBody Customer customer) {
        log.info("Sending message to Kafka: {}", customer);
        kafkaProducer.sendMessage(customer.getId(), customer);
        return ResponseEntity.ok().build();
    }
}
