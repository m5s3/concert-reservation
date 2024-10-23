package com.consertreservation.api.cron.UserToken;

import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserTokenCron {

    private static final int COUNT_OF_UPDATED_USER_TOKEN = 10;
    @Value("${schedule.userToken.success.active}")
    private boolean updateSuccess;
    @Value("${schedule.userToken.expire.active}")
    private boolean updateExpired;
    private final UserTokenService userTokenService;

    @Scheduled(cron = "${schedule.userToken.success.cron}")
    public void updateWaitToSuccess() {
        if (!updateSuccess) {
            return;
        }
        log.info("====== 대기열 유저 10명을 입장 시작 ======");
        List<ResultUserTokenServiceDto> userTokens = userTokenService.getWaitOfUserTokens(COUNT_OF_UPDATED_USER_TOKEN);
        userTokenService.updateUserTokensStatus(userTokens.stream().map(ResultUserTokenServiceDto::id).toList(), "SUCCESS");
        log.info("====== 대기열 유저 10명을 입장 종료 ======");
    }

    @Scheduled(cron = "${schedule.userToken.expire.cron}")
    public void updateSuccessToExpired() {
        if (!updateExpired) {
            return;
        }
        log.info("====== 입장한 유저 10명을 만료 시작 ======");
        List<ResultUserTokenServiceDto> userTokens = userTokenService.getSuccessOfUserTokens(COUNT_OF_UPDATED_USER_TOKEN);
        for (ResultUserTokenServiceDto userToken : userTokens) {
            log.info("userToken={}", userToken);
        }
        userTokenService.updateUserTokensStatus(userTokens.stream().map(ResultUserTokenServiceDto::id).toList(), "EXPIRED");
        log.info("====== 입장한 유저 10명을 만료 종료 ======");
    }
}
