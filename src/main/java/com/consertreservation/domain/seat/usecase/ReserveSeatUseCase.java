package com.consertreservation.domain.seat.usecase;

import com.consertreservation.domain.seat.usecase.dto.ResultReserveSeatUseCaseDto;
import java.time.LocalDateTime;

public interface ReserveSeatUseCase {

    ResultReserveSeatUseCaseDto execute(Long seatId, Long userId, Long concertId, LocalDateTime reserveDate);
}
