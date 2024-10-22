package com.consertreservation.domain.concert.usecase;

import com.consertreservation.domain.concert.application.ConcertService;
import com.consertreservation.domain.concert.components.ConcertComponent;
import com.consertreservation.domain.concert.components.dto.ConcertWithScheduleDto;
import com.consertreservation.domain.concert.usecase.dto.ResultConcertUseCaseDto;
import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchConcertUseCaseImpl implements SearchConcertUseCase {

    private final UserTokenComponent userTokenComponent;
    private final ConcertComponent concertComponent;

    public List<ResultConcertUseCaseDto> execute(Long userId, LocalDateTime dateTime) {
        userTokenComponent.validateAuthorization(userId);
        return concertComponent.getConcerts(dateTime)
                .stream().map(concert -> ResultConcertUseCaseDto.of(
                        concert.concertId(),
                        concert.title(),
                        concert.concertScheduleId(),
                        concert.reservationStartDate(),
                        concert.concertStartDate(),
                        concert.concertEndDate(),
                        concert.reservationSeat(),
                        concert.remainOfReservationOfSeat()
                ))
                .toList();
    }
}
