package com.consertreservation.api.usertoken;

import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenEventListener {

    private final UserTokenService userTokenService;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireUserTokenEventHandler(ExpiredUserTokenEvent event) {
        userTokenService.expireUserToken(event.userId());
    }

    @Async
    @KafkaListener(topics = "my-topic", groupId = "consumer-group01")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireUserTokenKafkaHandler(String payload) {
        log.info("Received user token event: {}", payload);
        Long userId = Long.parseLong(payload);
        userTokenService.expireUserToken(userId);
    }
}
