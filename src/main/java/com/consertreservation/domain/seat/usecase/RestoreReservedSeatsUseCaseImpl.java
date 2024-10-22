package com.consertreservation.domain.seat.usecase;

import com.consertreservation.domain.seat.components.ReservationSeatComponent;
import com.consertreservation.domain.seat.components.SeatComponent;
import com.consertreservation.domain.seat.components.dto.ReservationSeatDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestoreReservedSeatsUseCaseImpl implements RestoreReservedSeatsUseCase {

    private final ReservationSeatComponent reservationSeatComponent;
    private final SeatComponent seatComponent;

    @Override
    public void execute() {
        List<ReservationSeatDto> reservationSeats = reservationSeatComponent.getExpiredReservationSeats();
        reservationSeatComponent.changeReservationSeatsStatusToCancel();
        seatComponent.restoreReservedSeat(reservationSeats.stream().map(ReservationSeatDto::seatId).toList());
    }
}
