package com.consertreservation.domain.payment.usecase;

import com.consertreservation.domain.outbox.components.OutBoxComponent;
import com.consertreservation.domain.outbox.components.dto.ResultOutBox;
import com.consertreservation.domain.payment.components.PaymentComponent;
import com.consertreservation.domain.payment.components.dto.PaymentDto;
import com.consertreservation.domain.payment.usecase.dto.ResultPaymentUseCaseDto;
import com.consertreservation.domain.seat.components.ReservationSeatComponent;
import com.consertreservation.domain.seat.components.SeatComponent;
import com.consertreservation.domain.user.components.UserComponent;
import com.consertreservation.domain.usertoken.infra.event.ExpiredUserTokenEvent;
import com.consertreservation.domain.usertoken.producer.UserTokenPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayPaymentUseCaseImpl implements PayPaymentUseCase {

    private final ObjectMapper objectMapper;
    private final UserTokenPublisher publisher;
    private final PaymentComponent paymentComponent;
    private final ReservationSeatComponent reservationSeatComponent;
    private final SeatComponent seatComponent;
    private final UserComponent userComponent;
    private final OutBoxComponent outBoxComponent;

    @Override
    @Transactional
    public ResultPaymentUseCaseDto execute(long userId, long seatId, long amount) {
        seatComponent.validateFee(seatId, amount);
        reservationSeatComponent.completeReservation(seatId, userId);
        userComponent.spendFee(userId, amount);
        PaymentDto payment = paymentComponent.pay(userId, seatId, amount);
        ResultOutBox outBox = null;
        try {
            outBox = outBoxComponent.create(objectMapper.writeValueAsString(userId));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        publisher.expireUserTokenPublish(outBox);
        return ResultPaymentUseCaseDto.builder()
                .id(payment.id())
                .userId(payment.userId())
                .seatId(payment.seatId())
                .amount(payment.amount())
                .createdAt(payment.createdAt())
                .build();
    }
}
