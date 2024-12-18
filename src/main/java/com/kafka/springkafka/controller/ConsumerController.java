package com.kafka.springkafka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1")
@RequiredArgsConstructor
public class ConsumerController {
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private Boolean shouldStart = false;

    @GetMapping("/start-dlq")
    public String toggleKafkaListener() {
        shouldStart = !shouldStart;
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("id-1-dlq");
        if (listenerContainer != null) {
            if (Boolean.TRUE.equals(shouldStart)) {
                listenerContainer.start();
                return "started";
            } else {
                listenerContainer.stop();
                return "stopped";
            }
        } else {
            return "listenerContainer is null";
        }
    }
}

