package com.consertreservation.api.usertoken.dto;

import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import java.util.UUID;

public record ResponseUserToken(
        UUID id,
        long userId,
        String status,
        int waitingOrder
) {

    public static ResponseUserToken fromResultUserTokenServiceDto(ResultUserTokenServiceDto userTokenDto) {
        return new ResponseUserToken(userTokenDto.id(), userTokenDto.userId(), userTokenDto.tokenStatus(),
                userTokenDto.waitingOrder());
    }
}
