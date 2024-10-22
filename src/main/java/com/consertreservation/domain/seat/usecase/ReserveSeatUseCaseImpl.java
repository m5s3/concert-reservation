package com.consertreservation.domain.seat.usecase;

import com.consertreservation.domain.concert.components.ConcertScheduleComponent;
import com.consertreservation.domain.seat.components.ReservationSeatComponent;
import com.consertreservation.domain.seat.components.dto.ReservationSeatDto;
import com.consertreservation.domain.seat.usecase.dto.ResultReserveSeatUseCaseDto;
import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveSeatUseCaseImpl implements ReserveSeatUseCase {

    private final ConcertScheduleComponent concertScheduleComponent;
    private final ReservationSeatComponent reservationSeatComponent;
    private final UserTokenComponent userTokenComponent;

    @Override
    public ResultReserveSeatUseCaseDto execute(Long seatId, Long userId, Long concertId, LocalDateTime reserveDate) {
        validateReserveSeat(concertId, userId, reserveDate);
        concertScheduleComponent.decreaseRemainOfSeat(concertId);
        ReservationSeatDto reservationSeat = reservationSeatComponent.reserveSeat(seatId, userId);
        return ResultReserveSeatUseCaseDto.builder()
                .id(reservationSeat.id())
                .seatId(reservationSeat.seatId())
                .userId(reservationSeat.userId())
                .status(reservationSeat.status())
                .build();
    }

    private void validateReserveSeat(Long concertId, Long userId, LocalDateTime reserveDate) {
        userTokenComponent.validateAuthorization(userId);
        concertScheduleComponent.validateAvailableReservation(concertId, reserveDate);
    }
}
