package com.consertreservation.domain.payment.application;

import com.consertreservation.domain.payment.application.dto.ResultPaymentServiceDto;
import com.consertreservation.domain.payment.components.dto.PaymentDto;
import com.consertreservation.domain.payment.usecase.PayPaymentUseCase;
import com.consertreservation.domain.payment.usecase.dto.ResultPaymentUseCaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PayPaymentUseCase payPaymentUseCase;

    @Transactional
    public ResultPaymentServiceDto pay(long userId, long seatId, long amount) {
        ResultPaymentUseCaseDto payment = payPaymentUseCase.execute(userId, seatId, amount);
        return ResultPaymentServiceDto
                .builder()
                .id(payment.id())
                .userId(payment.userId())
                .seatId(payment.seatId())
                .amount(payment.amount())
                .createdAt(payment.createdAt())
                .build();
    }
}
