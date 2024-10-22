package com.consertreservation.domain.seat.application;

import com.consertreservation.domain.seat.application.dto.ResultReserveSeatServiceDto;
import com.consertreservation.domain.seat.usecase.ReserveSeatUseCase;
import com.consertreservation.domain.seat.usecase.RestoreReservedSeatsUseCase;
import com.consertreservation.domain.seat.usecase.dto.ResultReserveSeatUseCaseDto;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final RestoreReservedSeatsUseCase restoreReservedSeatsUseCase;
    private final ReserveSeatUseCase reserveSeatUseCase;

    public void restoreReservedSeats() {
        restoreReservedSeatsUseCase.execute();
    }

    public ResultReserveSeatServiceDto reserveSeat(Long seatId, Long userId, Long concertId, LocalDateTime reserveDate) {
        ResultReserveSeatUseCaseDto reserveSeat = reserveSeatUseCase.execute(seatId, userId, concertId, reserveDate);
        return ResultReserveSeatServiceDto.builder()
                .id(reserveSeat.id())
                .seatId(reserveSeat.seatId())
                .userId(reserveSeat.userId())
                .status((reserveSeat.status()))
                .build();
    }
}
