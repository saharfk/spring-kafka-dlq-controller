package com.kafka.springkafka.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.retrytopic.DefaultDestinationTopicResolver;
import org.springframework.kafka.retrytopic.DestinationTopicResolver;
import org.springframework.kafka.retrytopic.RetryTopicInternalBeanNames;

import java.time.Clock;
import java.util.Collections;

@EnableKafka
@Configuration
public class KafkaConfig {
    @Bean(name = RetryTopicInternalBeanNames.DESTINATION_TOPIC_CONTAINER_NAME)
    public DestinationTopicResolver destinationTopicResolver(ApplicationContext context) {
        DefaultDestinationTopicResolver resolver = new DefaultDestinationTopicResolver(Clock.systemUTC(), context);
        resolver.defaultFalse();
        resolver.setClassifications(Collections.emptyMap(), true);
        return resolver;
    }
}
