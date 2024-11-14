package com.consertreservation.api.usertoken;

import com.consertreservation.domain.usertoken.application.UserTokenService;
import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserTokenEventListener {

    private final UserTokenService userTokenService;

    @Async
    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void expireUserTokenEventHandler(ExpiredUserTokenEvent event) {
        userTokenService.expireUserToken(event.getUserId());
    }
}
