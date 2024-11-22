package com.consertreservation.api.cron.outbox;

import com.consertreservation.domain.outbox.application.OutBoxService;
import com.consertreservation.domain.outbox.application.dto.ResultServiceOutBox;
import com.consertreservation.domain.outbox.components.dto.ResultOutBox;
import com.consertreservation.domain.usertoken.producer.UserTokenPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutBoxCron {

    private final OutBoxService outBoxService;
    private final UserTokenPublisher publisher;

    @Scheduled(cron = "${schedule.outbox.re-publish.cron}")
    public void rePublish() {
        List<ResultServiceOutBox> resultServiceOutBoxes = outBoxService.showOutBoxs();
        resultServiceOutBoxes
                .stream()
                .filter(outbox -> outbox.updatedAt().isBefore(LocalDateTime.now().minusMinutes(5)))
                .forEach(resultServiceOutBox -> {
            publisher.expireUserTokenPublish(ResultOutBox.builder().id(resultServiceOutBox.id()).build());
        });
    }
}
