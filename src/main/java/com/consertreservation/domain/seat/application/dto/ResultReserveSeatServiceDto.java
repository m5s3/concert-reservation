package com.consertreservation.domain.seat.application.dto;

import com.consertreservation.domain.seat.model.ReservationSeatStatus;
import lombok.Builder;

@Builder
public record ResultReserveSeatServiceDto(
        Long id,
        Long userId,
        Long seatId,
        ReservationSeatStatus status
) {
}
