package com.consertreservation.domain.user.infra;

import static com.consertreservation.domain.user.model.QUser.user;

import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserReaderRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserReaderCustomRepository implements UserReaderRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<User> getUserWithLock(Long userId) {
        return Optional.ofNullable(
                        queryFactory.selectFrom(user)
                                .where(user.id.eq(userId))
                                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                        .fetchOne()
        );
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                        .where(user.id.eq(userId))
                        .fetchOne()
        );
    }

    @Override
    public Optional<User> getUserByName(String name) {
        return Optional.ofNullable(
                queryFactory.selectFrom(user)
                        .where(user.name.eq(name))
                        .fetchOne()
        );
    }
}
