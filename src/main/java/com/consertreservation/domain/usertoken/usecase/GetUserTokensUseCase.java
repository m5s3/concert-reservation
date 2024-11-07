package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetUserTokensUseCase {

    private final UserTokenComponent userTokenComponent;

    public List<ResultUserTokenUseCaseDto> executeUserTokens(List<Long> userIds) {
        return userTokenComponent.getUserTokens(userIds)
                .stream()
                .map(ResultUserTokenUseCaseDto::from)
                .toList();
    }
}
