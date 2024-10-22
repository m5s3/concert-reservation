package com.consertreservation.api.concert.dto;

import com.consertreservation.domain.concert.application.dto.ResultServiceDto;
import java.time.LocalDateTime;

public record ResponseConcert(
        Long concertId,
        String title,
        Long concertScheduleId,
        LocalDateTime reservationStartDate,
        LocalDateTime concertStartDate,
        LocalDateTime concertEndDate,
        int reservationSeat,
        int remainOfReservationOfSeat
) {

    public static ResponseConcert from(ResultServiceDto result) {
        return new ResponseConcert(result.concertId(), result.title(),
                result.concertScheduleId(),
                result.reservationStartDate(), result.concertStartDate(),
                result.concertEndDate(),
                result.reservationSeat(),
                result.remainOfReservationOfSeat());
    }
}
