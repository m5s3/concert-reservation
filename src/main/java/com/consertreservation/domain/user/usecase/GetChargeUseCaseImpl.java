package com.consertreservation.domain.user.usecase;

import com.consertreservation.domain.user.components.UserComponent;
import com.consertreservation.domain.user.components.dto.UserChargeDto;
import com.consertreservation.domain.user.usecase.dto.ResultChargeUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetChargeUseCaseImpl implements GetChargeUseCase {

    private final UserComponent userComponent;

    @Override
    public ResultChargeUseCaseDto execute(Long userId) {
        UserChargeDto charge = userComponent.getCharge(userId);
        return ResultChargeUseCaseDto.builder()
                .userId(charge.userId())
                .amount(charge.amount())
                .build();
    }
}
