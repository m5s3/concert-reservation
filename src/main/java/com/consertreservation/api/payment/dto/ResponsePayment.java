package com.consertreservation.api.payment.dto;

import com.consertreservation.domain.payment.application.dto.ResultPaymentServiceDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record ResponsePayment(
        Long userId,
        Long seatId,
        Long amount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

    public static ResponsePayment fromResultPaymentServiceDto(ResultPaymentServiceDto resultPaymentServiceDto) {
        return new ResponsePayment(resultPaymentServiceDto.userId(), resultPaymentServiceDto.seatId(),
                resultPaymentServiceDto.amount(), resultPaymentServiceDto.createdAt());
    }
}
