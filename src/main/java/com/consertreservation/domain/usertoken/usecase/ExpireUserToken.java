package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExpireUserToken {

    private final UserTokenComponent userTokenComponent;

    public void executeExpireUserToken(Long userId) {
        userTokenComponent.expireUserToken(userId);
    }
}
