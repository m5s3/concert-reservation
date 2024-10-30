package com.consertreservation.domain.seat.repository;

import com.consertreservation.domain.seat.model.Seat;
import java.util.List;

public interface SeatReaderRepository {
    Seat getSeat(Long id);
    Seat getSeatWithLock(Long id);
    List<Seat> getAvailableSeats(Long concertScheduleId);
    List<Seat> getSeats(List<Long> ids);
}
