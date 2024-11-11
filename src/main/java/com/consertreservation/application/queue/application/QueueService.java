package com.consertreservation.application.queue.application;

import com.consertreservation.application.redis.RedisService;
import com.consertreservation.application.queue.application.dto.ResultQueueServiceDto;
import com.consertreservation.application.queue.constant.QueueConst;
import com.consertreservation.domain.usertoken.application.UserTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final RedisService redisService;
    private final UserTokenService userTokenService;

    public void addItemToWaitingQueue(Long userId) {
        redisService.addItemToSortedSet(QueueConst.WAITING_QUEUE, String.valueOf(userId),
                getTimestamp(LocalDateTime.now()));
    }

    public void removeItemToWaitingQueue(int size) {
        redisService.removeItemsInSortedSet(QueueConst.WAITING_QUEUE, 0, size);
    }

    public void addItemToActiveQueue(Long userId) {
        redisService.addItemToHash(QueueConst.ACTIVE_QUEUE, String.valueOf(userId), "1");
    }

    public List<ResultQueueServiceDto> getNextItemsInWaitingQueue(int size) {
        List<Long> ids = redisService.getItemsInSortedSet(QueueConst.WAITING_QUEUE, 0, size)
                .stream()
                .map(userId -> Long.valueOf(String.valueOf(userId)))
                .toList();

        return userTokenService.getUserTokens(ids)
                .stream()
                .map(ResultQueueServiceDto::from)
                .toList();
    }

    public Long calculateRemainOfWaitingOrder(Long userId) {
       return redisService.getItemRank(QueueConst.WAITING_QUEUE, String.valueOf(userId)) + 1;
    }

    public boolean isInActiveQueue(Long userId) {
        return redisService.existInHash(QueueConst.ACTIVE_QUEUE, String.valueOf(userId));
    }

    public void clearActiveQueue() {
        redisService.deleteAllInHash(QueueConst.ACTIVE_QUEUE);
    }

    private long getTimestamp(LocalDateTime date) {
        return Timestamp.valueOf(date).getTime();
    }
}
