package com.consertreservation.domain.usertoken.application;

import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import com.consertreservation.domain.usertoken.usecase.GetSuccessOfUserTokensUseCase;
import com.consertreservation.domain.usertoken.usecase.GetUserTokenUseCase;
import com.consertreservation.domain.usertoken.usecase.GetWaitOfUserTokensUseCase;
import com.consertreservation.domain.usertoken.usecase.UpdateStatusUseCase;
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
        return ResultUserTokenServiceDto.builder()
                .id(userToken.id())
                .userId(userToken.userId())
                .tokenStatus(userToken.tokenStatus())
                .waitingOrder(userToken.waitingOrder())
                .build();
    }
}
