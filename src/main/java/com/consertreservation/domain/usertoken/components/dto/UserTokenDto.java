package com.consertreservation.domain.usertoken.components.dto;

import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import java.util.UUID;

public record UserTokenDto(
        UUID id,
        long userId,
        String tokenStatus,
        int waitingOrder
) {

    public static UserTokenDto from(UserToken userToken) {
        return new UserTokenDto(userToken.getId(), userToken.getUserId(), userToken.getStatus().name(), userToken.getWaitingOrder());
    }

    public static UserToken toEntity(UserTokenDto dto) {
        return UserToken.builder()
                .id(dto.id)
                .userid(dto.userId)
                .status(TokenStatus.find(dto.tokenStatus))
                .build();
    }
}
