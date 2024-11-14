package com.consertreservation.domain.usertoken.infra.event;

import lombok.Getter;

@Getter
public class ExpiredUserTokenEvent {

    private final Long userId;

    public ExpiredUserTokenEvent(Long userId) {
        this.userId = userId;
    }
}
