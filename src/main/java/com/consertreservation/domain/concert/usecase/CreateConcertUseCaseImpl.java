package com.consertreservation.domain.concert.usecase;

import com.consertreservation.domain.concert.components.ConcertComponent;
import com.consertreservation.domain.concert.components.ConcertScheduleComponent;
import com.consertreservation.domain.concert.components.dto.ConcertDto;
import com.consertreservation.domain.concert.components.dto.ConcertScheduleDto;
import com.consertreservation.domain.concert.usecase.dto.ResultConcertUseCaseDto;
import com.consertreservation.domain.seat.components.SeatComponent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateConcertUseCaseImpl implements CreateConcertUseCase {

    private static int COUNT_OF_SEAT = 50;

    private final ConcertComponent concertComponent;
    private final ConcertScheduleComponent concertScheduleComponent;
    private final SeatComponent seatComponent;

    @Override
    public ResultConcertUseCaseDto execute(String title, LocalDateTime reservationStateDate,
            LocalDateTime concertStartDate, LocalDateTime concertEndDate, int reservationSeat) {
        ConcertDto concert = concertComponent.createConcert(title);
        ConcertScheduleDto concertSchedule = concertScheduleComponent.createConcertSchedule(concert.id(),
                reservationStateDate,
                concertStartDate,
                concertEndDate,
                reservationSeat);
        generateSeats(concertSchedule);
        return ResultConcertUseCaseDto.ofConcertAndConcertSchedule(concert, concertSchedule);
    }

    private void generateSeats(ConcertScheduleDto concertSchedule) {
        for(int i = 1; i <= COUNT_OF_SEAT; i ++) {
            seatComponent.createSeat(concertSchedule.id(), i);
        }
    }
}
