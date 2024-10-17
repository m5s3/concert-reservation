package com.consertreservation.api.usecase;

import com.consertreservation.domain.payment.components.PaymentComponent;
import com.consertreservation.domain.payment.components.dto.PaymentDto;
import com.consertreservation.domain.seat.components.ReservationSeatComponent;
import com.consertreservation.domain.seat.components.SeatComponent;
import com.consertreservation.domain.user.components.UserComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentUseCase {

    private final PaymentComponent paymentComponent;
    private final ReservationSeatComponent reservationSeatComponent;
    private final SeatComponent seatComponent;
    private final UserComponent userComponent;

    public PaymentDto pay(long userId, long seatId, long amount) {
        seatComponent.validateFee(seatId, amount);
        reservationSeatComponent.completeReservation(seatId, userId);
        userComponent.spendFee(userId, amount);
        return paymentComponent.pay(userId, seatId, amount);
    }
}