package com.consertreservation.domain.seat.usecase.dto;

import com.consertreservation.domain.seat.model.ReservationSeatStatus;
import lombok.Builder;

@Builder
public record ResultReserveSeatUseCaseDto(
        Long id,
        Long userId,
        Long seatId,
        ReservationSeatStatus status
) {
}
