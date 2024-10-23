package com.consertreservation.domain.user.usecase;

import com.consertreservation.domain.user.usecase.dto.ResultChargeUseCaseDto;

public interface GetChargeUseCase {
    ResultChargeUseCaseDto execute(Long userId);
}
