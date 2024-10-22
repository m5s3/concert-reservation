package com.consertreservation.domain.concert.application.dto;

import java.time.LocalDateTime;

public record ResultServiceDto(
        Long concertId,
        String title,
        Long concertScheduleId,
        LocalDateTime reservationStartDate,
        LocalDateTime concertStartDate,
        LocalDateTime concertEndDate,
        int reservationSeat,
        int remainOfReservationOfSeat
) {

    public static ResultServiceDto of(
            Long concertId,
            String title,
            Long concertScheduleId,
            LocalDateTime reservationStartDate,
            LocalDateTime concertStartDate,
            LocalDateTime concertEndDate,
            int reservationSeat,
            int remainOfReservationOfSeat
    ) {
        return new ResultServiceDto(concertId, title, concertScheduleId, reservationStartDate, concertStartDate,
                concertEndDate, reservationSeat, remainOfReservationOfSeat);
    }

}
