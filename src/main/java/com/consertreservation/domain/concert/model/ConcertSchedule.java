package com.consertreservation.domain.concert.model;

import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.FULL_RESERVATION_SEAT;
import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.INVALID_RESERVATION_DATE;

import com.consertreservation.domain.concert.exception.ConcertScheduleException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_schedule_id")
    private Long id;

    private Long concertId;
    private LocalDateTime reservationStateDate;
    private LocalDateTime concertStartDate;
    private LocalDateTime concertEndDate;
    private int reservationSeat;
    private int remainOfReservationOfSeat;

    @Builder
    public ConcertSchedule(Long id, Long concertId, LocalDateTime reservationStateDate, LocalDateTime concertStartDate,
            LocalDateTime concertEndDate, int reservationSeat, int remainOfReservationOfSeat) {
        this.id = id;
        this.concertId = concertId;
        this.reservationStateDate = reservationStateDate;
        this.concertStartDate = concertStartDate;
        this.concertEndDate = concertEndDate;
        this.reservationSeat = reservationSeat;
        this.remainOfReservationOfSeat = remainOfReservationOfSeat;
    }

    public boolean isAvailableSchedule(LocalDateTime date) {
        validateDate(date);
        validateSeat();
        return true;
    }

    private void validateSeat() {
        if (remainOfReservationOfSeat >= reservationSeat) {
            throw new ConcertScheduleException(FULL_RESERVATION_SEAT, "예약이 가득 찼습니다");
        }
    }

    private void validateDate(LocalDateTime date) {
        if (this.reservationStateDate.isAfter(date) ||
                this.concertStartDate.isBefore(date)) {
            throw new ConcertScheduleException(INVALID_RESERVATION_DATE,
                    "예약 시작일은 %s 이고 예약 종료일은 %s 입니다. 요청 받은 날짜는 %s 입니다".formatted(
                            reservationStateDate, concertStartDate, date));
        }
    }
}