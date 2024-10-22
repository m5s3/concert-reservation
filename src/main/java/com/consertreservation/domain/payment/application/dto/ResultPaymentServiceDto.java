package com.consertreservation.domain.payment.application.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResultPaymentServiceDto(
        Long id,
        Long userId,
        Long seatId,
        Long amount,
        LocalDateTime createdAt
) {

}
