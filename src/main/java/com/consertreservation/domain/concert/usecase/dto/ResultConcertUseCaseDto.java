package com.consertreservation.domain.concert.usecase.dto;

import com.consertreservation.domain.concert.components.dto.ConcertDto;
import com.consertreservation.domain.concert.components.dto.ConcertScheduleDto;
import java.time.LocalDateTime;

public record ResultConcertUseCaseDto(
        Long concertId,
        String title,
        Long concertScheduleId,
        LocalDateTime reservationStartDate,
        LocalDateTime concertStartDate,
        LocalDateTime concertEndDate,
        int reservationSeat,
        int remainOfReservationOfSeat
) {

    public static ResultConcertUseCaseDto ofConcertAndConcertSchedule(ConcertDto concert, ConcertScheduleDto concertSchedule) {
        return new ResultConcertUseCaseDto(
                concert.id(),
                concert.title(),
                concertSchedule.id(),
                concertSchedule.reservationStateDate(),
                concertSchedule.concertStartDate(),
                concertSchedule.concertEndDate(),
                concertSchedule.reservationSeat(),
                concertSchedule.remainOfReservationOfSeat()
        );
    }

    public static ResultConcertUseCaseDto of(
            Long concertId,
            String title,
            Long concertScheduleId,
            LocalDateTime reservationStartDate,
            LocalDateTime concertStartDate,
            LocalDateTime concertEndDate,
            int reservationSeat,
            int remainOfReservationOfSeat
    ) {
        return new ResultConcertUseCaseDto(
                concertId,
                title,
                concertScheduleId,
                reservationStartDate,
                concertStartDate,
                concertEndDate,
                reservationSeat,
                remainOfReservationOfSeat
        );
    }
}
