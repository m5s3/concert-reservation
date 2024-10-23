package com.consertreservation.domain.usertoken.model;

import java.util.Arrays;

public enum TokenStatus {
    WAIT, SUCCESS, EXPIRED;

    public static TokenStatus find(String status) {
        return Arrays.stream(values())
                .filter(tokenStatus -> tokenStatus.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 토큰 상태는 존재하지 않습니다. " + status));
    }
}
