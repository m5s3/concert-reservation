package com.consertreservation.api.payment;

import com.consertreservation.api.payment.dto.RequestPayment;
import com.consertreservation.api.payment.dto.ResponsePayment;
import com.consertreservation.domain.payment.application.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<ResponsePayment> payment(@RequestBody RequestPayment request) {
        return ResponseEntity.ok()
                .body(ResponsePayment.fromResultPaymentServiceDto(
                        paymentService.pay(request.userId(), request.seatId(), request.amount())));
    }
}
