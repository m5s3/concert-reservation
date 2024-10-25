package com.consertreservation.domain.payment.infra;

import static com.consertreservation.domain.payment.model.QPayment.payment;

import com.consertreservation.domain.payment.model.Payment;
import com.consertreservation.domain.payment.repository.PaymentReaderRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCustomReadRepository implements PaymentReaderRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Payment> showPayments(long userId) {
        return queryFactory.selectFrom(payment)
                .where(payment.userId.eq(userId))
                .fetch();
    }
}
