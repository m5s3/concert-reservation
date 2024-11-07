package com.consertreservation.application.queue.application.dto;

import com.consertreservation.domain.usertoken.application.dto.ResultUserTokenServiceDto;
import lombok.Builder;

@Builder
public record ResultQueueServiceDto(
        Long userId,
        Long waitingOrder,
        String tokenStatus
) {

    public static ResultQueueServiceDto from(ResultUserTokenServiceDto dto) {
        return ResultQueueServiceDto.builder()
                .userId(dto.userId())
                .tokenStatus(dto.tokenStatus())
                .build();
    }
}
