package com.consertreservation.api.cron.UserToken;

import com.consertreservation.application.queue.application.QueueService;
import com.consertreservation.application.queue.application.dto.ResultQueueServiceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.consertreservation.application.queue.constant.QueueConst.COUNT_OF_ACTIVE_USER_TOKEN;

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
        queueService.clearActiveQueue();
        List<ResultQueueServiceDto> items = queueService.getNextItemsInWaitingQueue(COUNT_OF_ACTIVE_USER_TOKEN);
        items.forEach(item -> queueService.addItemToActiveQueue(item.userId()));
        queueService.removeItemToWaitingQueue(COUNT_OF_ACTIVE_USER_TOKEN);
        log.info("====== 대기열 유저 250명을 입장 종료 ======");
    }
}
