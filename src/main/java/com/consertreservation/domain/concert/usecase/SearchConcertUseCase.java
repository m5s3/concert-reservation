package com.consertreservation.domain.concert.usecase;

import com.consertreservation.domain.concert.usecase.dto.ResultConcertUseCaseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchConcertUseCase {

    List<ResultConcertUseCaseDto> execute(Long userId, LocalDateTime dateTime);
}
