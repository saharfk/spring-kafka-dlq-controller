package com.kafka.springkafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.FixedDelayStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Non-blocking retry consumer using single retry topic strategy with fixed backoff policy.
 */
@Slf4j
@Component
@SuppressWarnings("unused")
public class SingleTopicRetryConsumer {

    @RetryableTopic(
            attempts = "2",
            backoff = @Backoff(delay = 1000),
            fixedDelayTopicStrategy = FixedDelayStrategy.SINGLE_TOPIC,
            include = {Exception.class},
            includeNames = "java.lang.Exception",
            autoStartDltHandler = "false",
            dltTopicSuffix = "-dlq",
            numPartitions = "2")
    @KafkaListener(id = "id-1", topics = "products-main")
    @Transactional(value = "kafkaTransactionManager", noRollbackFor = Exception.class)
    public void listen(ConsumerRecord<String, String> message) {

        log.info("SingleTopicRetryConsumer: message consumed - \nkey: {} , \nvalue: {}, \ntopic: {}",
                message.key(),
                message.value(),
                message.topic());
        throw new RuntimeException("Exception in main consumer");
    }

    @DltHandler
    @Transactional(value = "kafkaTransactionManager")
    public void dltListener(ConsumerRecord<String, String> message) {
        log.info("SingleTopicRetryConsumer: message consumed at DLT - \nkey: {} , \nvalue: {}, \ntopic: {}",
                message.key(),
                message.value(),
                message.topic());
    }
}
