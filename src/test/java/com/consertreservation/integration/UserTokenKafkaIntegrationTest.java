package com.consertreservation.integration;

import com.consertreservation.domain.outbox.application.OutBoxService;
import com.consertreservation.domain.outbox.components.dto.ResultOutBox;
import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.producer.UserTokenPublisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(
        partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092"
        },
        ports = {9092},
        topics = {"my-topic"}
)
public class UserTokenKafkaIntegrationTest {

    @Autowired
    UserTokenPublisher userTokenPublisher;

    @MockBean
    OutBoxService outBoxService;

    @Test
    @DisplayName("카프카 producer 및 consumer 테스트")
    void publishExpireUserTokenEvent() {
        userTokenPublisher.expireUserTokenPublish(ResultOutBox.builder().id(1L).build());

        await()
                .atMost(5, TimeUnit.SECONDS)
                .pollInterval(Duration.ofSeconds(1))
                .until(() -> {
                    verify(outBoxService, atLeastOnce()).showOutBox(any(Long.class));
                    return true;
                });
    }
}
