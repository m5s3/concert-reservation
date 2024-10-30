package com.consertreservation.domain.concert.model;

import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.FULL_RESERVATION_SEAT;
import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.INVALID_RESERVATION_DATE;

import com.consertreservation.domain.base.BaseTimeEntity;
import com.consertreservation.domain.concert.exception.ConcertScheduleException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConcertSchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_schedule_id")
    private Long id;

    private Long concertId;
    private LocalDateTime reservationStartDate;
    private LocalDateTime concertStartDate;
    private LocalDateTime concertEndDate;
    private int reservationSeat;
    private int remainOfReservationSeat;

    @Version
    private Long version;

    @Builder
    public ConcertSchedule(Long id, Long concertId, LocalDateTime reservationStartDate, LocalDateTime concertStartDate,
            LocalDateTime concertEndDate, int reservationSeat, int remainOfReservationSeat) {
        this.id = id;
        this.concertId = concertId;
        this.reservationStartDate = reservationStartDate;
        this.concertStartDate = concertStartDate;
        this.concertEndDate = concertEndDate;
        this.reservationSeat = reservationSeat;
        this.remainOfReservationSeat = remainOfReservationSeat;
    }

    public void validateAvailable(LocalDateTime date) {
        validateDate(date);
        validateSeat();
    }

    private void validateSeat() {
        if (remainOfReservationSeat == 0) {
            throw new ConcertScheduleException(FULL_RESERVATION_SEAT, "예약이 가득 찼습니다");
        }
    }

    private void validateDate(LocalDateTime date) {
        if (this.reservationStartDate.isAfter(date) ||
                this.concertStartDate.isBefore(date)) {
            throw new ConcertScheduleException(INVALID_RESERVATION_DATE,
                    "예약 시작일은 %s 이고 예약 종료일은 %s 입니다. 요청 받은 날짜는 %s 입니다".formatted(
                            reservationStartDate, concertStartDate, date));
        }
    }

    public void decreaseRemainOfSeat() {
        if (this.remainOfReservationSeat < 1) {
            throw new ConcertScheduleException(FULL_RESERVATION_SEAT, "예약이 가득 찼습니다");
        }
        this.remainOfReservationSeat--;
    }
}
