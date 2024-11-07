package com.consertreservation.domain.usertoken.respositories;

import com.consertreservation.domain.usertoken.model.UserToken;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTokenReaderRepository {
    Optional<UserToken> getUserToken(Long userId);
    Long getWaitOfUserTokenCount();
    List<UserToken> getWaitOfUserTokensLimited(int count);
    List<UserToken> getSuccessOfUserTokensLimited(int count);
    List<UserToken> getUserTokens(List<UUID> id);
    List<UserToken> getUserTokensByUserId(List<Long> userIds);
}
