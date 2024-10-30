package com.consertreservation.domain.seat.model;

import static com.consertreservation.domain.seat.exception.SeatErrorCode.ALREADY_RESERVED;
import static com.consertreservation.domain.seat.exception.SeatErrorCode.INVALID_LESS_FEE;
import static com.consertreservation.domain.seat.exception.SeatErrorCode.INVALID_MORE_FEE;

import com.consertreservation.domain.base.BaseTimeEntity;
import com.consertreservation.domain.seat.exception.SeatException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    private Long concertScheduleId;
    private int seatNumber;
    private long fee;

    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition = "varchar(20)")
    private SeatStatus status;

    @Version
    private Long version;

    @Builder
    public Seat(Long id, Long concertScheduleId, int seatNumber, SeatStatus status, long fee) {
        this.id = id;
        this.concertScheduleId = concertScheduleId;
        this.seatNumber = seatNumber;
        this.status = status;
        this.fee = fee;
    }

    public void reserve() {
        if (isReserved()) {
            throw new SeatException(ALREADY_RESERVED, "이미 예약된 좌석입니다");
        }
        this.status = SeatStatus.RESERVED;
    }

    private boolean isReserved() {
        return status == SeatStatus.RESERVED;
    }

    public void validateFee(long amount) {
        if (this.fee < amount) {
            throw new SeatException(INVALID_MORE_FEE, "좌석 금액보다 더 큰 결제 금액입니다");
        }

        if (this.fee > amount) {
            throw new SeatException(INVALID_LESS_FEE, "좌석 금액보다 더 적은 결제 금액입니다");
        }
    }

    public void restoreStatus() {
        this.status = SeatStatus.AVAILABLE;
    }
}
