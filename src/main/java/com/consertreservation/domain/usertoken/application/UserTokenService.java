package com.consertreservation.domain.usertoken.application;

import com.consertreservation.application.queue.application.QueueService;
import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import com.consertreservation.domain.usertoken.usecase.*;
import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTokenService {

    private final UpdateStatusUseCase updateStatusUseCase;
    private final GetSuccessOfUserTokensUseCase getSuccessOfUserTokensUseCase;
    private final GetWaitOfUserTokensUseCase getWaitOfUserTokensUseCase;
    private final GetUserTokenUseCase getUserTokenUseCase;
    private final IsAuthorizedUseCase isAuthorizedUseCase;
    private final GetUserTokensUseCase getUserTokensUseCase;

    private final QueueService queueService;

    public void updateUserTokensStatus(List<UUID> userTokenIds, String status) {
        updateStatusUseCase.execute(userTokenIds, status);
    }

    @Transactional(readOnly = true)
    public List<ResultUserTokenServiceDto> getSuccessOfUserTokens(int count) {
        return getSuccessOfUserTokensUseCase.execute(count)
                .stream()
                .map(userToken -> ResultUserTokenServiceDto.builder()
                        .id(userToken.id())
                        .userId(userToken.userId())
                        .tokenStatus(userToken.tokenStatus())
                        .waitingOrder(userToken.waitingOrder())
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResultUserTokenServiceDto> getWaitOfUserTokens(int count) {
        return getWaitOfUserTokensUseCase.execute(count)
                .stream()
                .map(userToken -> ResultUserTokenServiceDto.builder()
                        .id(userToken.id())
                        .userId(userToken.userId())
                        .tokenStatus(userToken.tokenStatus())
                        .waitingOrder(userToken.waitingOrder())
                        .build())
                .toList();
    }

    public ResultUserTokenServiceDto getUserToken(Long userId) {
        ResultUserTokenUseCaseDto userToken = getUserTokenUseCase.execute(userId);
        // 대기열에 추가
        queueService.addItemToWaitingQueue(userToken.userId());

        // 대기 순서 조회
        int waitingOrder = queueService.calculateRemainOfWaitingOrder(userId).intValue();
        return ResultUserTokenServiceDto.builder()
                .id(userToken.id())
                .userId(userToken.userId())
                .tokenStatus(userToken.tokenStatus())
                .waitingOrder(waitingOrder)
                .build();
    }

    public boolean isAuthorized(Long userId) {
        return queueService.isInActiveQueue(userId);
    }

    public List<ResultUserTokenServiceDto> getUserTokens(List<Long> userIds) {
        return getUserTokensUseCase.executeUserTokens(userIds)
                .stream()
                .map(ResultUserTokenServiceDto::from)
                .toList();
    }
}
