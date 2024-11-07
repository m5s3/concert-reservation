package com.consertreservation.domain.usertoken.application.dto;

import java.util.UUID;

import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import lombok.Builder;

@Builder
public record ResultUserTokenServiceDto(
        UUID id,
        long userId,
        String tokenStatus,
        int waitingOrder
) {

    public static ResultUserTokenServiceDto from(ResultUserTokenUseCaseDto usertoken) {
        return ResultUserTokenServiceDto
                .builder()
                .id(usertoken.id())
                .userId(usertoken.userId())
                .tokenStatus(usertoken.tokenStatus())
                .build();
    }
}
