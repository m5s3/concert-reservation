package com.consertreservation.api.usecase;

import static com.consertreservation.domain.usertoken.exception.UserTokenErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.*;

import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import com.consertreservation.domain.concert.repository.ConcertStoreRepository;
import com.consertreservation.domain.seat.application.SeatService;
import com.consertreservation.domain.seat.application.dto.ResultSeatServiceDto;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;
import com.consertreservation.domain.seat.repository.SeatStoreRepository;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeatUserCaseTest {

    @Autowired
    SeatService seatService;

    @Autowired
    UserTokenStoreRepository userTokenStoreRepository;

    @Autowired
    ConcertStoreRepository concertStoreRepository;

    @Autowired
    UserStoreRepository userStoreRepository;

    @Autowired
    SeatStoreRepository seatStoreRepository;

    @Autowired
    ConcertScheduleStoreRepository concertScheduleStoreRepository;

    @Test
    @DisplayName("권한이 없는 유저가 이용가능한 좌석을 조회를 하면 예외가 발생한다")
    void get_available_seat_unauthorized_test() {
        // 유저 및 유저토큰 생성
        int remainOfReservationSeat = 1;
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.WAIT);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), remainOfReservationSeat);
        ConcertSchedule concertSchedule = concertScheduleStoreRepository.save(newConcertSchedule);

        // When & Then
        assertThatThrownBy(() -> seatService.getAvailableSeats(user.getId(), concertSchedule.getId()))
                .isInstanceOf(UserTokenException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED);
    }

    @Test
    @DisplayName("권한이 있는 유저는 이용가능한 좌석을 조회 할 수가 있다")
    void get_available_seat_authorized_test() {
        // Given
        // 유저 및 유저토큰 생성
        int remainOfReservationSeat = 1;
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), remainOfReservationSeat);

        ConcertSchedule concertSchedule = concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        createSeat(newConcertSchedule, 1);
        createSeat(newConcertSchedule, 2);
        createSeat(newConcertSchedule, 3);

        // When
        List<ResultSeatServiceDto> seats = seatService.getAvailableSeats(user.getId(),
                concertSchedule.getId());

        // Then
        assertThat(seats).hasSize(3);
    }

    private Seat createSeat(ConcertSchedule newConcertSchedule, int seatNumber) {
        Seat seat = Seat.builder()
                .seatNumber(seatNumber)
                .status(SeatStatus.AVAILABLE)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        return seatStoreRepository.save(seat);
    }

    private User createUserAndUserToken(String name, TokenStatus tokenStatus) {
        User user = User.builder()
                .name(name)
                .build();
        User newUser = userStoreRepository.save(user);
        UserToken newUserToken = UserToken.builder()
                .id(UUID.randomUUID())
                .userid(newUser.getId())
                .status(tokenStatus)
                .build();
        userTokenStoreRepository.save(newUserToken);
        return newUser;
    }

    private static ConcertSchedule createConcertSchedule(Concert newConcert, LocalDateTime reservationStartDate,
            LocalDateTime concertStartDate, LocalDateTime concertEndDate, int remainOfReservationSeat) {
        return ConcertSchedule.builder()
                .concertId(newConcert.getId())
                .reservationStartDate(reservationStartDate)
                .concertStartDate(concertStartDate)
                .concertEndDate(concertEndDate)
                .remainOfReservationSeat(remainOfReservationSeat)
                .build();
    }

    private Concert createConcert(String title) {
        Concert concert = Concert.builder()
                .title(title)
                .build();
        return concertStoreRepository.save(concert);
    }
}