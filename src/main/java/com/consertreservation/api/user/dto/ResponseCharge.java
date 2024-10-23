package com.consertreservation.api.user.dto;

import com.consertreservation.domain.user.application.dto.ResultChargeServiceDto;

public record ResponseCharge(
        Long userId,
        long amount
) {
    public static ResponseCharge of(Long userId, long amount) {
        return new ResponseCharge(userId, amount);
    }
    public static ResponseCharge fromResultServiceDto(ResultChargeServiceDto dto) {
        return new ResponseCharge(dto.userId(), dto.amount());
    }
}
