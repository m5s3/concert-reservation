package com.consertreservation.domain.seat.infra;

import static com.consertreservation.domain.seat.model.QSeat.seat;

import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;
import com.consertreservation.domain.seat.repository.SeatReaderRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SeatReaderCustomRepository implements SeatReaderRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Seat getSeat(Long id) {
        return queryFactory.selectFrom(seat)
                .where(seat.id.eq(id))
                .fetchFirst();
    }

    @Override
    public Seat getSeatWithLock(Long id) {
        return queryFactory.selectFrom(seat)
                .where(seat.id.eq(id))
                .setLockMode(LockModeType.OPTIMISTIC)
                .fetchFirst();
    }

    @Override
    public List<Seat> getAvailableSeats(Long concertScheduleId) {
        return queryFactory.selectFrom(seat)
                .where(seat.concertScheduleId.eq(concertScheduleId))
                .where(seat.status.eq(SeatStatus.AVAILABLE))
                .fetch();
    }

    @Override
    public List<Seat> getSeats(List<Long> ids) {
        return queryFactory.selectFrom(seat)
                .where(seat.id.in(ids))
                .fetch();
    }
}
