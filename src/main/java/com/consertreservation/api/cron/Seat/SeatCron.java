package com.consertreservation.api.cron.Seat;

import com.consertreservation.domain.seat.application.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SeatCron {

    @Value("${schedule.reserveSeat.restore.active}")
    private boolean restoreReservedSeatFlag;
    private final SeatService seatService;

    @Scheduled(cron = "${schedule.reserveSeat.restore.cron}")
    public void restoreReservedSeat() {
        if (!restoreReservedSeatFlag) {
            return;
        }
        log.info("====== 10분 이내에 예악이 완료되지 않은 죄석 예약 이용 가능하도록 수정 작업 시작 ======");
        seatService.restoreReservedSeats();
        log.info("====== 10분 이내에 예악이 완료되지 않은 죄석 예약 이용 가능하도록 수정 작업 종료 ======");
    }
}
