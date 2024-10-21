package com.consertreservation.domain.usertoken.infra;

import static com.consertreservation.domain.usertoken.model.QUserToken.userToken;

import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenReaderRepository;
import com.querydsl.jpa.JPQLQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserTokenReaderCustomRepository implements UserTokenReaderRepository {

    private final JPQLQueryFactory queryFactory;

    @Override
    public Optional<UserToken> getUserToken(Long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(userToken)
                .where(userToken.userId.eq(userId))
                .orderBy(userToken.createdAt.desc())
                .fetchFirst());
    }

    @Override
    public Long getWaitOfUserTokenCount() {
        return queryFactory.select(userToken.count())
                .from(userToken)
                .where(userToken.status.eq(TokenStatus.WAIT))
                .fetchCount();
    }

    @Override
    public List<UserToken> getWaitOfUserTokensLimited(int count) {
        return queryFactory.selectFrom(userToken)
                .where(userToken.status.eq(TokenStatus.WAIT))
                .orderBy(userToken.createdAt.asc())
                .limit(count)
                .fetch();
    }

    @Override
    public List<UserToken> getSuccessOfUserTokensLimited(int count) {
        return queryFactory.selectFrom(userToken)
                .where(userToken.status.eq(TokenStatus.SUCCESS))
                .where(userToken.updatedAt.before(LocalDateTime.now().minusMinutes(30)))
                .orderBy(userToken.updatedAt.asc())
                .limit(count)
                .fetch();
    }

    @Override
    public List<UserToken> getUserTokens(List<UUID> ids) {
        return queryFactory.selectFrom(userToken)
                .where(userToken.id.in(ids))
                .fetch();
    }
}
