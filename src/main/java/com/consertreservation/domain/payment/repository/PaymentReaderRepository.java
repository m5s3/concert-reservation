package com.consertreservation.domain.payment.repository;

import com.consertreservation.domain.payment.model.Payment;
import com.consertreservation.domain.user.model.User;
import java.util.List;
import java.util.Optional;

public interface PaymentReaderRepository {

    List<Payment> showPayments(long userId);
}
