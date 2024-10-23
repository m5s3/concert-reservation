package com.consertreservation.domain.user.application;

import com.consertreservation.domain.user.application.dto.ResultChargeServiceDto;
import com.consertreservation.domain.user.usecase.ChargeUserCase;
import com.consertreservation.domain.user.usecase.GetChargeUseCase;
import com.consertreservation.domain.user.usecase.dto.ResultChargeUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final GetChargeUseCase getChargeUseCase;
    private final ChargeUserCase chargeUserCase;

    public ResultChargeServiceDto getCharge(Long userId) {
        ResultChargeUseCaseDto userToken = getChargeUseCase.execute(userId);
        return ResultChargeServiceDto.builder()
                .userId(userToken.userId())
                .amount(userToken.amount())
                .build();
    }

    public ResultChargeServiceDto charge(Long userId, Long amount) {
        ResultChargeUseCaseDto charge = chargeUserCase.execute(userId, amount);
        return ResultChargeServiceDto.builder()
                .userId(charge.userId())
                .amount(charge.amount())
                .build();
    }
}
