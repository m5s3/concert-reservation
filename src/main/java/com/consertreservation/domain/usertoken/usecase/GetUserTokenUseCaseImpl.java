package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserTokenUseCaseImpl implements GetUserTokenUseCase{

    private final UserTokenComponent userTokenComponent;

    @Override
    public ResultUserTokenUseCaseDto execute(Long userId) {
        UserTokenDto userToken = userTokenComponent.showUserToken(userId)
                .orElseGet(() -> userTokenComponent.createToken(userId));

        if (userTokenComponent.isExpired(userToken.userId())) {
            userToken = userTokenComponent.createToken(userId);
        }

        return ResultUserTokenUseCaseDto.builder()
                .id(userToken.id())
                .userId(userToken.userId())
                .tokenStatus(userToken.tokenStatus())
                .waitingOrder(userToken.waitingOrder())
                .build();
    }
}
