package com.consertreservation.domain.seat.application;

import com.consertreservation.common.lock.annotation.DistributedLock;
import com.consertreservation.domain.seat.application.dto.ResultReserveSeatServiceDto;
import com.consertreservation.domain.seat.application.dto.ResultSeatServiceDto;
import com.consertreservation.domain.seat.usecase.GetAvailableSeatsUseCase;
import com.consertreservation.domain.seat.usecase.ReserveSeatUseCase;
import com.consertreservation.domain.seat.usecase.RestoreReservedSeatsUseCase;
import com.consertreservation.domain.seat.usecase.dto.ResultReserveSeatUseCaseDto;
import com.consertreservation.domain.seat.usecase.dto.ResultSeatUseCaseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final RestoreReservedSeatsUseCase restoreReservedSeatsUseCase;
    private final ReserveSeatUseCase reserveSeatUseCase;
    private final GetAvailableSeatsUseCase getAvailableSeatsUseCase;

    public void restoreReservedSeats() {
        restoreReservedSeatsUseCase.execute();
    }

    @DistributedLock(key = "#seatId")
    public ResultReserveSeatServiceDto reserveSeat(Long seatId, Long userId, Long concertId, LocalDateTime reserveDate) {
        log.info("userId={}", userId);
        ResultReserveSeatUseCaseDto reserveSeat = reserveSeatUseCase.execute(seatId, userId, concertId, reserveDate);
        return ResultReserveSeatServiceDto.builder()
                .id(reserveSeat.id())
                .seatId(reserveSeat.seatId())
                .userId(reserveSeat.userId())
                .status((reserveSeat.status().name()))
                .build();
    }

    @Transactional(readOnly = true)
    public List<ResultSeatServiceDto> getAvailableSeats(Long userId, Long concertScheduleId) {
        List<ResultSeatUseCaseDto> seats = getAvailableSeatsUseCase.execute(userId, concertScheduleId);
        return seats.stream().map(seat -> ResultSeatServiceDto.builder()
                        .id(seat.id())
                        .seatNumber(seat.seatNumber())
                        .fee(seat.fee())
                        .status(seat.status())
                        .build())
                .toList();
    }
}
