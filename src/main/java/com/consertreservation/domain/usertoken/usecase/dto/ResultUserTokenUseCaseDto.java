package com.consertreservation.domain.usertoken.usecase.dto;

import java.util.UUID;

import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import lombok.Builder;

@Builder
public record ResultUserTokenUseCaseDto(
        UUID id,
        long userId,
        String tokenStatus,
        int waitingOrder
) {

    public static ResultUserTokenUseCaseDto from(UserTokenDto userTokenDto){
        return ResultUserTokenUseCaseDto.builder()
                .id(userTokenDto.id())
                .userId(userTokenDto.userId())
                .tokenStatus(userTokenDto.tokenStatus())
                .build();
    }
}
