package com.demo.kafka.controller;

import com.demo.kafka.dto.Customer;
import com.demo.kafka.message.KafkaProducer;
import com.demo.kafka.service.EmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {

    private final KafkaProducer kafkaProducer;
    private final EmailService emailService;

    @PostMapping(value = "/api/message/send") // APPLICATION_NDJSON_VALUE
    public ResponseEntity<Void> sendMessage(@Valid @RequestBody Customer customer) throws ExecutionException, InterruptedException {
        log.info("Sending message to Kafka: {}", customer);
        kafkaProducer.sendMessage(customer.id(), customer);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/api/email/send")
    public ResponseEntity<Void> sendEmail() {
        final String to = "emmanuel.mura@gmail.com";
        log.info("Sending email to: {}", to);
        emailService.sendSimpleEmail(to, "Email from Spring Boot", "This is a test email from Spring Boot");
        log.info("Email sent successfully to: {}", to);
        return ResponseEntity.ok().build();
    }
}
