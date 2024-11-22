package com.consertreservation.api.usertoken;

import com.consertreservation.domain.outbox.application.OutBoxService;
import com.consertreservation.domain.outbox.application.dto.ResultServiceOutBox;
import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenEventListener {

    private final UserTokenService userTokenService;
    private final OutBoxService outBoxService;
    private final ObjectMapper objectMapper;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireUserTokenEventHandler(ExpiredUserTokenEvent event) {
        userTokenService.expireUserToken(event.userId());
    }

    @Async
    @KafkaListener(topics = "my-topic", groupId = "consumer-group01")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireUserTokenKafkaHandler(String payload) throws JsonProcessingException {
        log.info("Received user token event: {}", payload);
        try {
            ResultServiceOutBox resultServiceOutBox = outBoxService.showOutBox(Long.parseLong(payload));
            if (resultServiceOutBox.status().equals("INIT")) {
                outBoxService.updateOutBoxToReceived(resultServiceOutBox.id());
            } else {
                throw new RuntimeException("이미 처리된 메세지입니다");
            }
            Long userId = objectMapper.readValue(resultServiceOutBox.payload(), Long.class);
            userTokenService.expireUserToken(userId);
            log.info("유저 토큰 만료 완료");
            outBoxService.updateOutBoxToSuccess(resultServiceOutBox.id());
        } catch (RuntimeException e) {
            log.info(e.getMessage());
        }
    }
}
