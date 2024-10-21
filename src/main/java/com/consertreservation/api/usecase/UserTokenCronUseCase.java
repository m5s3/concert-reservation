package com.consertreservation.api.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTokenCronUseCase {

    private final UserTokenComponent userTokenComponent;
    private final int countOfUpdatedUserToken = 10;
    @Value("${schedule.userToken.success.active}")
    private boolean updateSuccess;
    @Value("${schedule.userToken.expire.active}")
    private boolean updateExpired;

    @Scheduled(cron = "${schedule.userToken.success.cron}")
    public void updateWaitToSuccess() {
        if (!updateSuccess) {
            return;
        }
        log.info("updateWaitToSuccess");
        List<UserTokenDto> userTokens = userTokenComponent.getWaitOfUserTokens(countOfUpdatedUserToken);
        userTokenComponent.updateUserTokensStatus(userTokens, TokenStatus.SUCCESS);
    }

    @Scheduled(cron = "${schedule.userToken.expire.cron}")
    public void updateSuccessToExpired() {
        if (!updateExpired) {
            return;
        }
        log.info("updateSuccessToExpired");
        List<UserTokenDto> userTokens = userTokenComponent.getSuccessOfUserTokens(countOfUpdatedUserToken);
        for (UserTokenDto userToken : userTokens) {
            log.info("userToken={}", userToken);
        }
        userTokenComponent.updateUserTokensStatus(userTokens, TokenStatus.EXPIRED);
    }
}
