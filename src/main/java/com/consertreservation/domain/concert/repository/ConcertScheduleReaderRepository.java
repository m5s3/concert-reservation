package com.consertreservation.domain.concert.repository;

import com.consertreservation.domain.concert.model.ConcertSchedule;
import java.time.LocalDateTime;
import java.util.List;

public interface ConcertScheduleReaderRepository {

    ConcertSchedule getConcertSchedule(Long concertId);
    ConcertSchedule getConcertScheduleWithLock(Long concertId);
    List<ConcertSchedule> getConcertSchedules(List<Long> concertIds);
    List<ConcertSchedule> getConcertSchedules(LocalDateTime dateTime);
}
