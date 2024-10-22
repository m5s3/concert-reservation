package com.consertreservation.domain.concert.application;

import com.consertreservation.domain.concert.application.dto.ResultServiceDto;
import com.consertreservation.domain.concert.usecase.CreateConcertUseCase;
import com.consertreservation.domain.concert.usecase.SearchConcertUseCase;
import com.consertreservation.domain.concert.usecase.dto.ResultConcertUseCaseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertService {

    private final CreateConcertUseCase createConcertUsecase;
    private final SearchConcertUseCase searchConcertUseCase;

    public ResultServiceDto createConcert(
            String title,
            LocalDateTime reservationStateDate,
            LocalDateTime concertStartDate,
            LocalDateTime concertEndDate,
            int reservationSeat
    ) {
        ResultConcertUseCaseDto concert = createConcertUsecase.execute(title, reservationStateDate, concertStartDate,
                concertEndDate,
                reservationSeat);
        return ResultServiceDto.of(
                concert.concertId(),
                concert.title(),
                concert.concertScheduleId(),
                concert.reservationStartDate(),
                concert.concertStartDate(),
                concert.concertEndDate(),
                concert.reservationSeat(),
                concert.remainOfReservationOfSeat()
        );
    }

    public List<ResultServiceDto> searchConcertByDate(Long userId, LocalDateTime dateTime) {
        List<ResultConcertUseCaseDto> concerts = searchConcertUseCase.execute(userId, dateTime);
        return concerts.stream().map(concert -> ResultServiceDto.of(concert.concertId(),
                concert.title(),
                concert.concertScheduleId(),
                concert.reservationStartDate(),
                concert.concertStartDate(),
                concert.concertEndDate(),
                concert.reservationSeat(),
                concert.remainOfReservationOfSeat())).toList();
    }
}
