package com.consertreservation.domain.concert.usecase;

import com.consertreservation.domain.concert.usecase.dto.ResultConcertUseCaseDto;
import java.time.LocalDateTime;

public interface CreateConcertUseCase {

    ResultConcertUseCaseDto execute(String title,
            LocalDateTime reservationStateDate,
            LocalDateTime concertStartDate,
            LocalDateTime concertEndDate,
            int reservationSeat);
}
