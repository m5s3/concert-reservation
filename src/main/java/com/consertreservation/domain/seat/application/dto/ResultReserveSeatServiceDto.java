package com.consertreservation.domain.seat.application.dto;

import lombok.Builder;

@Builder
public record ResultReserveSeatServiceDto(
        Long id,
        Long userId,
        Long seatId,
        String status
) {
}
