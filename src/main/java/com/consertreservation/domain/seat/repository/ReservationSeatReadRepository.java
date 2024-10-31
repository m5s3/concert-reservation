package com.consertreservation.domain.seat.repository;

import com.consertreservation.domain.seat.model.ReservationSeat;
import java.util.List;
import java.util.Optional;

public interface ReservationSeatReadRepository {
    boolean isReservedSeat(Long seatId, Long userId);
    Optional<ReservationSeat> getReservationSeat(Long seatId, Long userId);
    Optional<ReservationSeat> getReservationSeat(Long seatId);
    List<ReservationSeat> getExpiredReservationSeats();
}
