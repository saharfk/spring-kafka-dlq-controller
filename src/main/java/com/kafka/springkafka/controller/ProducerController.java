package com.kafka.springkafka.controller;

import com.kafka.springkafka.producer.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProducerController {
    private final Producer producer;

    @PostMapping("/send-message")
    public ResponseEntity<String> sendToQueue(@RequestBody Map<String, String> message) {
        producer.send("products-main", message.get("key"), message.get("value"));
        return ResponseEntity.ok("sent to queue");
    }
}
