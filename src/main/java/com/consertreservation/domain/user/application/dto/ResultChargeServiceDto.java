package com.consertreservation.domain.user.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record ResultChargeServiceDto(
        Long userId,
        long amount
) {
}
