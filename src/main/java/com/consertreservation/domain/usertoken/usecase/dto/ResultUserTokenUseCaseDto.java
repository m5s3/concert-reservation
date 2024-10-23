package com.consertreservation.domain.usertoken.usecase.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ResultUserTokenUseCaseDto(
        UUID id,
        long userId,
        String tokenStatus,
        int waitingOrder
) {
}
