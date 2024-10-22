package com.consertreservation.api.concert;

import com.consertreservation.api.concert.dto.CreateRequestConcertDto;
import com.consertreservation.api.concert.dto.ResponseConcert;
import com.consertreservation.domain.concert.application.ConcertService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/concert")
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @PostMapping
    public ResponseEntity<ResponseConcert> createConcert(@RequestBody CreateRequestConcertDto request) {
        return ResponseEntity.ok().body(ResponseConcert.from(concertService.createConcert(
                request.title(),
                request.reservationStateDate(),
                request.concertStartDate(),
                request.concertEndDate(),
                request.reservationSeat()
        )));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ResponseConcert>> getConcerts(@RequestParam("user_id") Long userId,
            @RequestParam("date") LocalDateTime dateTime) {
        return ResponseEntity.ok().body(
                concertService.searchConcertByDate(userId, dateTime)
                        .stream()
                        .map(ResponseConcert::from)
                        .toList()
        );
    }
}
