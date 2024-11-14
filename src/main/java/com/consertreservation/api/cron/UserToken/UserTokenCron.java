package com.consertreservation.api.cron.UserToken;

import com.consertreservation.application.queue.application.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenCron {

    @Value("${schedule.userToken.success.active}")
    private boolean updateSuccess;
    @Value("${schedule.userToken.expire.active}")
    private boolean updateExpired;
    private final QueueService queueService;

    @Scheduled(cron = "${schedule.userToken.success.cron}")
    public void updateWaitToSuccess() {
        if (!updateSuccess) {
            return;
        }
        log.info("====== 대기열 유저 250명을 입장 시작 ======");
        queueService.activeUserToken();
        log.info("====== 대기열 유저 250명을 입장 종료 ======");
    }
}
