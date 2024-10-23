package com.consertreservation.domain.user.usecase;

import com.consertreservation.domain.user.usecase.dto.ResultChargeUseCaseDto;

public interface ChargeUserCase {
    ResultChargeUseCaseDto execute(Long userId, Long amount);
}
