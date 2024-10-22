package com.consertreservation.api.seat.dto;

import com.consertreservation.domain.seat.application.dto.ResultSeatServiceDto;

public record ResponseSeatDto(
        Long id,
        int seatNumber,
        long fee
) {

    public static ResponseSeatDto fromResultSeatServiceDto(ResultSeatServiceDto seat) {
        return new ResponseSeatDto(seat.id(), seat.seatNumber(), seat.fee());
    }
}
