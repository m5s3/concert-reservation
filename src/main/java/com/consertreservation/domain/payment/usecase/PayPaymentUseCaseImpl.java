package com.consertreservation.domain.payment.usecase;

import com.consertreservation.domain.payment.components.PaymentComponent;
import com.consertreservation.domain.payment.components.dto.PaymentDto;
import com.consertreservation.domain.payment.usecase.dto.ResultPaymentUseCaseDto;
import com.consertreservation.domain.seat.components.ReservationSeatComponent;
import com.consertreservation.domain.seat.components.SeatComponent;
import com.consertreservation.domain.user.components.UserComponent;
import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayPaymentUseCaseImpl implements PayPaymentUseCase {

    private final PaymentComponent paymentComponent;
    private final ReservationSeatComponent reservationSeatComponent;
    private final SeatComponent seatComponent;
    private final UserComponent userComponent;
    private final UserTokenComponent userTokenComponent;

    @Override
    public ResultPaymentUseCaseDto execute(long userId, long seatId, long amount) {
        seatComponent.validateFee(seatId, amount);
        reservationSeatComponent.completeReservation(seatId, userId);
        userComponent.spendFee(userId, amount);
        userTokenComponent.expireUserToken(userId);
        PaymentDto payment = paymentComponent.pay(userId, seatId, amount);
        return ResultPaymentUseCaseDto.builder()
                .id(payment.id())
                .userId(payment.userId())
                .seatId(payment.seatId())
                .amount(payment.amount())
                .createdAt(payment.createdAt())
                .build();
    }
}
