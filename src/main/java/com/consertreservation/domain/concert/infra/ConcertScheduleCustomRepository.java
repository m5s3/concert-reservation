package com.consertreservation.domain.concert.infra;

import static com.consertreservation.domain.concert.model.QConcertSchedule.concertSchedule;

import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleReaderRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleCustomRepository implements ConcertScheduleReaderRepository {

    private final JPQLQueryFactory queryFactory;

    @Override
    public ConcertSchedule getConcertSchedule(Long concertId) {
        return queryFactory.selectFrom(concertSchedule)
                .where(concertSchedule.concertId.eq(concertId))
                .fetchOne();
    }

    @Override
    public List<ConcertSchedule> getConcertSchedules(List<Long> concertIds) {
        return queryFactory.selectFrom(concertSchedule)
                .where(concertSchedule.concertId.in(concertIds))
                .fetch();
    }

    @Override
    public List<ConcertSchedule> getConcertSchedules(LocalDateTime dateTime) {
        return queryFactory.selectFrom(concertSchedule)
                .where(concertSchedule.reservationStartDate.before(dateTime))
                .where(concertSchedule.concertStartDate.after(dateTime))
                .fetch();
    }
}
