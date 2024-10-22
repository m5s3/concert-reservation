package com.consertreservation.domain.seat.application.dto;

import lombok.Builder;

@Builder
public record ResultSeatServiceDto(
        Long id,
        Long concertScheduleId,
        int seatNumber,
        long fee,
        String status
) {
}
