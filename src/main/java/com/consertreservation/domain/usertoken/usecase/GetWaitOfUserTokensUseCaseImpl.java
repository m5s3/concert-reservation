package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetWaitOfUserTokensUseCaseImpl implements GetWaitOfUserTokensUseCase {

    private final UserTokenComponent userTokenComponent;

    @Override
    public List<ResultUserTokenUseCaseDto> execute(int count) {
        return userTokenComponent.getWaitOfUserTokens(count)
                .stream()
                .map(userToken -> ResultUserTokenUseCaseDto.builder()
                        .id(userToken.id())
                        .userId(userToken.userId())
                        .tokenStatus(userToken.tokenStatus())
                        .waitingOrder(userToken.waitingOrder())
                        .build())
                .toList();
    }
}
