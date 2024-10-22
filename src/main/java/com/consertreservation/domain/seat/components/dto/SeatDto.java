package com.consertreservation.domain.seat.components.dto;

import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;

public record SeatDto(
        Long id,
        Long concertScheduleId,
        int seatNumber,
        long fee,
        SeatStatus status
) {

    public static SeatDto from(Seat seat) {
        return new SeatDto(seat.getId(), seat.getConcertScheduleId(), seat.getSeatNumber(), seat.getFee(),
                seat.getStatus());
    }
}
