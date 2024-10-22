package com.consertreservation.api.seat;

import com.consertreservation.api.seat.dto.RequestReservationSeat;
import com.consertreservation.api.seat.dto.ResponseSeatDto;
import com.consertreservation.api.seat.dto.ResponseReservationSeat;
import com.consertreservation.domain.seat.application.SeatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<ResponseSeatDto>> showSeatsByConcertSchedule(@RequestParam("user_id") Long userId,
            @RequestParam("concert_schedule_id") Long concertScheduleId) {
        return ResponseEntity.ok().body(
                seatService.getAvailableSeats(userId, concertScheduleId)
                        .stream()
                        .map(ResponseSeatDto::fromResultSeatServiceDto)
                        .toList()
        );
    }

    @PostMapping("/reserve")
    public ResponseEntity<ResponseReservationSeat> reserveSeat(
            @RequestBody RequestReservationSeat request) {
        return ResponseEntity.ok()
                .body(ResponseReservationSeat.fromResultReserveSeatServiceDto(seatService.reserveSeat(request.seatId(), request.userId(), request.concertId(), request.reservationDate())));
    }
}
