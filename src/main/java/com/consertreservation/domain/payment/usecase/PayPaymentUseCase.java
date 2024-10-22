package com.consertreservation.domain.payment.usecase;

import com.consertreservation.domain.payment.usecase.dto.ResultPaymentUseCaseDto;

public interface PayPaymentUseCase {

    ResultPaymentUseCaseDto execute(long userId, long seatId, long amount);
}
