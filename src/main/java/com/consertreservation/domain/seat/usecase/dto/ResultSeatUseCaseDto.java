package com.consertreservation.domain.seat.usecase.dto;

import lombok.Builder;

@Builder
public record ResultSeatUseCaseDto(
        Long id,
        Long concertScheduleId,
        int seatNumber,
        long fee,
        String status
) {
}
