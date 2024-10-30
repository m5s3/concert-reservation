package com.consertreservation.domain.concert.components;

import com.consertreservation.domain.concert.components.dto.ConcertScheduleDto;
import com.consertreservation.domain.concert.infra.ConcertScheduleCustomRepository;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ConcertScheduleComponent {

    private final ConcertScheduleCustomRepository concertScheduleCustomRepository;
    private final ConcertScheduleStoreRepository concertScheduleStoreRepository;

    public void validateAvailableReservation(long concertId, LocalDateTime date) {
        ConcertSchedule concertSchedule = concertScheduleCustomRepository.getConcertScheduleWithLock(concertId);
        ConcertScheduleValidator.validate(concertSchedule);
        concertSchedule.validateAvailable(date);
    }

    public void decreaseRemainOfSeat(long concertId) {
        //ConcertSchedule concertSchedule = concertScheduleCustomRepository.getConcertSchedule(concertId);
        ConcertSchedule concertSchedule = concertScheduleCustomRepository.getConcertScheduleWithLock(concertId);
        concertSchedule.decreaseRemainOfSeat();
    }

    public ConcertScheduleDto showConcertSchedule(long concertId) {
        return ConcertScheduleDto.from(concertScheduleCustomRepository.getConcertSchedule(concertId));
    }

    public ConcertScheduleDto createConcertSchedule(
            Long concertId,
            LocalDateTime reservationStateDate,
            LocalDateTime concertStartDate,
            LocalDateTime concertEndDate,
            int reservationSeat
    ) {
        ConcertSchedule concertSchedule = ConcertSchedule.builder()
                .concertId(concertId)
                .reservationStartDate(reservationStateDate)
                .concertStartDate(concertStartDate)
                .concertEndDate(concertEndDate)
                .reservationSeat(reservationSeat)
                .build();
        return ConcertScheduleDto.from(concertScheduleStoreRepository.save(concertSchedule));
    }
}
