package com.consertreservation.domain.outbox.infa;

import com.consertreservation.domain.outbox.model.OutBox;
import com.consertreservation.domain.outbox.model.OutBoxStatus;
import com.consertreservation.domain.outbox.repository.OutBoxReaderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.consertreservation.domain.outbox.model.QOutBox.outBox;

@Repository
@RequiredArgsConstructor
public class OutBoxCustomRepository implements OutBoxReaderRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<OutBox> getOutBox(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(outBox)
                .where(outBox.id.eq(id))
                .fetchFirst());
    }

    @Override
    public List<OutBox> getOutBoxesByStatus(OutBoxStatus status) {
        return queryFactory.selectFrom(outBox)
                .where(outBox.status.eq(status))
                .fetch();
    }
}
