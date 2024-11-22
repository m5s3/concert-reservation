package com.consertreservation.domain.usertoken.infra.event;

import lombok.Builder;

@Builder
public record ExpiredUserTokenEvent(
        Long userId
) {

    public static ExpiredUserTokenEvent withUserId(Long userId) {
        return ExpiredUserTokenEvent.builder()
                .userId(userId)
                .build();
    }
}
