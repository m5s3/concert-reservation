package com.consertreservation.domain.seat.infra;

import static com.consertreservation.domain.seat.model.QReservationSeat.reservationSeat;

import com.consertreservation.domain.seat.model.ReservationSeat;
import com.consertreservation.domain.seat.model.ReservationSeatStatus;
import com.consertreservation.domain.seat.repository.ReservationSeatReadRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReservationSeatCustomRepository implements ReservationSeatReadRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isReservedSeat(Long seatId, Long userId) {
        return queryFactory.from(reservationSeat)
                .where(reservationSeat.userId.eq(userId))
                .where(reservationSeat.seatId.eq(seatId))
                .where(reservationSeat.status.ne(ReservationSeatStatus.CANCELLED))
                .fetchFirst() != null;
    }

    @Override
    public Optional<ReservationSeat> getReservationSeat(Long seatId, Long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(reservationSeat)
                .where(reservationSeat.userId.eq(userId))
                .where(reservationSeat.seatId.eq(seatId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchFirst());
    }

    @Override
    public List<ReservationSeat> getExpiredReservationSeats() {
        return queryFactory.selectFrom(reservationSeat)
                .where(reservationSeat.status.eq(ReservationSeatStatus.ING))
                .where(reservationSeat.updatedAt.before(LocalDateTime.now().minusMinutes(5)))
                .fetch();
    }
}
