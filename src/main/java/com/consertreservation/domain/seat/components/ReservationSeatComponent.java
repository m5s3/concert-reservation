package com.consertreservation.domain.seat.components;

import static com.consertreservation.domain.seat.exception.ReservationErrorCode.ALREADY_IN_SEAT;
import static com.consertreservation.domain.seat.exception.ReservationErrorCode.NOT_FOUND;

import com.consertreservation.domain.seat.components.dto.ReservationSeatDto;
import com.consertreservation.domain.seat.exception.ReservationSeatException;
import com.consertreservation.domain.seat.model.ReservationSeat;
import com.consertreservation.domain.seat.model.ReservationSeatStatus;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.repository.ReservationSeatReadRepository;
import com.consertreservation.domain.seat.repository.ReservationSeatStoreRepository;
import com.consertreservation.domain.seat.repository.SeatReaderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ReservationSeatComponent {

    private final SeatReaderRepository seatReaderRepository;
    private final ReservationSeatStoreRepository reservationSeatStoreRepository;
    private final ReservationSeatReadRepository reservationSeatReadRepository;

    public ReservationSeatDto reserveSeat(Long seatId, Long userId) {
        if (reservationSeatReadRepository.isReservedSeat(seatId, userId)) {
            throw new ReservationSeatException(ALREADY_IN_SEAT, "이미 예약했습니다");
        }
        Seat seat = seatReaderRepository.getSeat(seatId);
        seat.reserve();
        ReservationSeat reservationSeat = ReservationSeat.builder()
                .seatId(seatId)
                .userId(userId)
                .status(ReservationSeatStatus.ING)
                .build();
        return ReservationSeatDto.from(reservationSeatStoreRepository.save(reservationSeat));
    }

    public void completeReservation(Long seatId, Long userId) {
        ReservationSeat reservationSeat = reservationSeatReadRepository.getReservationSeat(seatId, userId)
                .orElseThrow(() -> new ReservationSeatException(NOT_FOUND, "예약 좌석을 찾을 수 없습니다"));
        reservationSeat.completeReservation();
    }

    @Transactional(readOnly = true)
    public List<ReservationSeatDto> getExpiredReservationSeats() {
        return reservationSeatReadRepository.getExpiredReservationSeats().stream().map(ReservationSeatDto::from)
                .toList();
    }

    public void changeReservationSeatsStatusToCancel() {
        reservationSeatReadRepository.getExpiredReservationSeats().forEach(ReservationSeat::cancelReservation);
    }
}
