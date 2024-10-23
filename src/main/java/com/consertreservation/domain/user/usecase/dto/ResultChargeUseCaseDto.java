package com.consertreservation.domain.user.usecase.dto;

import lombok.Builder;

@Builder
public record ResultChargeUseCaseDto(
        Long userId,
        long amount
) {
}
