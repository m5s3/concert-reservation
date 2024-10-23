package com.consertreservation.domain.user.application.dto;

import lombok.Builder;

@Builder
public record ResultChargeServiceDto(
        Long userId,
        long amount
) {
}
