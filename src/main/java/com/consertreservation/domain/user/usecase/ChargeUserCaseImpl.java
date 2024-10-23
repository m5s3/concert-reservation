package com.consertreservation.domain.user.usecase;

import com.consertreservation.domain.user.components.UserComponent;
import com.consertreservation.domain.user.components.dto.UserChargeDto;
import com.consertreservation.domain.user.usecase.dto.ResultChargeUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChargeUserCaseImpl implements ChargeUserCase {

    private final UserComponent userComponent;

    @Override
    public ResultChargeUseCaseDto execute(Long userId, Long amount) {
        UserChargeDto charge = userComponent.charge(userId, amount);
        return ResultChargeUseCaseDto.builder()
                .userId(charge.userId())
                .amount(charge.amount())
                .build();
    }
}
