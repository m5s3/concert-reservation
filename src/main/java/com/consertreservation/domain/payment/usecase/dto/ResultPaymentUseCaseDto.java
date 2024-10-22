package com.consertreservation.domain.payment.usecase.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResultPaymentUseCaseDto(
        Long id,
        Long userId,
        Long seatId,
        Long amount,
        LocalDateTime createdAt
) {
}
