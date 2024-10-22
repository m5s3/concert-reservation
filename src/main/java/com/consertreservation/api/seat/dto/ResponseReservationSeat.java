package com.consertreservation.api.seat.dto;

import com.consertreservation.domain.seat.application.dto.ResultReserveSeatServiceDto;

public record ResponseReservationSeat(
        Long id,
        Long seatId,
        Long userId,
        String Status
) {

    public static ResponseReservationSeat fromResultReserveSeatServiceDto(ResultReserveSeatServiceDto reservationSeatDto) {
        return new ResponseReservationSeat(reservationSeatDto.id(),
                reservationSeatDto.seatId(), reservationSeatDto.userId(), reservationSeatDto.status());
    }
}
