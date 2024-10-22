package com.consertreservation.domain.seat.usecase;

import com.consertreservation.domain.seat.usecase.dto.ResultSeatUseCaseDto;
import java.util.List;

public interface GetAvailableSeatsUseCase {

    List<ResultSeatUseCaseDto> execute(Long userId, Long concertScheduleId);
}
