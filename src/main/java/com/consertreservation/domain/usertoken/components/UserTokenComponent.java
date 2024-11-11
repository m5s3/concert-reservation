package com.consertreservation.domain.usertoken.components;

import static com.consertreservation.domain.usertoken.exception.UserTokenErrorCode.NOT_FOUND;

import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenReaderRepository;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
@Transactional
public class UserTokenComponent {

    private final UserTokenStoreRepository userTokenStoreRepository;
    private final UserTokenReaderRepository userTokenReaderRepository;

    public UserTokenDto createToken(long userId) {
        UserToken userToken = UserToken.builder()
                .userid(userId)
                .status(TokenStatus.WAIT)
                .build();
        UserToken newUserToken = userTokenStoreRepository.save(userToken);
        newUserToken.updateWaitingOrder(calculateWaitingOrder());
        return UserTokenDto.from(newUserToken);
    }

    @Transactional(readOnly = true)
    public Optional<UserTokenDto> showUserToken(long userId) {
        Optional<UserToken> userToken = userTokenReaderRepository.getUserToken(userId);
        return userToken.map(UserTokenDto::from);
    }

    @Transactional(readOnly = true)
    public boolean isExpired(long userId) {
        log.info("isExpired");
        UserToken userToken = userTokenReaderRepository.getUserToken(userId)
                .orElseThrow(() -> new UserTokenException(NOT_FOUND, "해당 유저 토큰이 없습니다"));
        return userToken.isExpire();
    }

    private int calculateWaitingOrder() {
        return userTokenReaderRepository.getWaitOfUserTokenCount().intValue();
    }

    @Transactional(readOnly = true)
    public List<UserTokenDto> showWaitOfUserToken(int count) {
        return userTokenReaderRepository.getWaitOfUserTokensLimited(count)
                .stream().map(UserTokenDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserTokenDto> getWaitOfUserTokens(int count) {
        return userTokenReaderRepository.getWaitOfUserTokensLimited(count)
                .stream().map(UserTokenDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserTokenDto> getSuccessOfUserTokens(int count) {
        return userTokenReaderRepository.getSuccessOfUserTokensLimited(count)
                .stream().map(UserTokenDto::from)
                .toList();
    }

    public void updateUserTokensStatus(List<UUID> ids, String status) {
        List<UserToken> userTokens = userTokenReaderRepository.getUserTokens(ids);
        userTokens.forEach(userToken -> userToken.changeStatus(TokenStatus.find(status)));
    }

    public void expireUserToken(long userId) {
        UserToken userToken = userTokenReaderRepository.getUserToken(userId)
                .orElseThrow(() -> new UserTokenException(NOT_FOUND, "유저 토큰을 찾을 수 없습니다"));
        userToken.expire();
    }

    @Transactional(readOnly = true)
    public List<UserTokenDto> getUserTokens(List<Long> userIds) {
        return userTokenReaderRepository.getUserTokensByUserId(userIds)
                .stream()
                .map(UserTokenDto::from)
                .toList();
    }
}
