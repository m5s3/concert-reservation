package com.consertreservation.domain.usertoken.infra;

import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import com.consertreservation.domain.usertoken.producer.UserTokenPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenKafkaPublisher implements UserTokenPublisher {

    @Value("${topic.name}")
    private String topic;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void expireUserTokenPublish(ExpiredUserTokenEvent event) {
        log.info("Expired UserTokenEvent: {}", event);
        kafkaTemplate.send(topic, String.valueOf(event.userId()), String.valueOf(event.userId()));
    }
}
