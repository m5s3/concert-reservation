package com.consertreservation.domain.seat.usecase;

import com.consertreservation.domain.seat.components.SeatComponent;
import com.consertreservation.domain.seat.components.dto.SeatDto;
import com.consertreservation.domain.seat.usecase.dto.ResultSeatUseCaseDto;
import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAvailableSeatsUseCaseImpl implements GetAvailableSeatsUseCase{

    private final UserTokenComponent userTokenComponent;
    private final SeatComponent seatComponent;

    @Override
    public List<ResultSeatUseCaseDto> execute(Long userId, Long concertScheduleId) {
        List<SeatDto> seats = seatComponent.getAvailableSeats(concertScheduleId);
        return seats.stream()
                .map(seat -> ResultSeatUseCaseDto.builder()
                        .id(seat.id())
                        .seatNumber(seat.seatNumber())
                        .fee(seat.fee())
                        .concertScheduleId(seat.concertScheduleId())
                        .status(seat.status().name()).build())
                .toList();

    }
}
