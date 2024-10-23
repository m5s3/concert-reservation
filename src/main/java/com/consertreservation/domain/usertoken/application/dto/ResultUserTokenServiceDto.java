package com.consertreservation.domain.usertoken.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ResultUserTokenServiceDto(
        UUID id,
        long userId,
        String tokenStatus,
        int waitingOrder
) {
}
