package com.consertreservation.domain.concert.infra;

import static com.consertreservation.domain.concert.model.QConcert.concert;
import static com.consertreservation.domain.concert.model.QConcertSchedule.concertSchedule;

import com.consertreservation.domain.concert.components.ConcertComponent;
import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.repository.ConcertReaderRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertReaderCustomRepository implements ConcertReaderRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Concert> getConcerts(List<Long> concertIds) {
        return queryFactory.selectFrom(concert)
                .where(concert.id.in(concertIds))
                .fetch();
    }
}
