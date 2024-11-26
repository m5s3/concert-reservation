package com.consertreservation.api.support;

import com.consertreservation.domain.concert.exception.ConcertScheduleException;
import com.consertreservation.domain.seat.exception.ReservationSeatException;
import com.consertreservation.domain.seat.exception.SeatException;
import com.consertreservation.domain.user.exception.UserException;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    // START seat exception handler
    @ExceptionHandler(ConcertScheduleException.class)
    public ResponseEntity<String> handleConcertScheduleException(ConcertScheduleException e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }

    @ExceptionHandler(SeatException.class)
    public ResponseEntity<String> handleSeatException(SeatException e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }

    @ExceptionHandler(ReservationSeatException.class)
    public ResponseEntity<String> handleReservationException(ReservationSeatException e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }
    // END seat exception handler

    // START user exception handler
    @ExceptionHandler(UserTokenException.class)
    public ResponseEntity<String> handleSeatException(UserTokenException e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }
    // END user exception handler

    // START payment exception handler
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleSeatException(UserException e) {
        return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
    }
    // END payment exception handler
}
