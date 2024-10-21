package com.consertreservation.api.usecase;

import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.FULL_RESERVATION_SEAT;
import static com.consertreservation.domain.concert.exception.ConcertScheduleErrorCode.INVALID_RESERVATION_DATE;
import static com.consertreservation.domain.usertoken.exception.UserTokenErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.consertreservation.domain.concert.exception.ConcertScheduleException;
import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleReaderRepository;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import com.consertreservation.domain.concert.repository.ConcertStoreRepository;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.repository.SeatStoreRepository;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReserveSeatUseCaseTest {

    @Autowired
    ReserveSeatUseCase reserveSeatUseCase;

    @Autowired
    UserTokenStoreRepository userTokenStoreRepository;

    @Autowired
    ConcertScheduleReaderRepository concertScheduleReaderRepository;

    @Autowired
    ConcertStoreRepository concertStoreRepository;

    @Autowired
    ConcertScheduleStoreRepository concertScheduleStoreRepository;

    @Autowired
    SeatStoreRepository seatStoreRepository;

    @Autowired
    UserStoreRepository userStoreRepository;

    @Test
    @DisplayName("권한이 없는 유저가 예약을 하면 예외가 발생한다")
    void reserve_seat_unauthorized_test() {
        // Given
        // 유저 및 유저토큰 생성
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.WAIT);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), 50);
        concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        Seat seat = Seat.builder()
                .seatNumber(1)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        Seat newSeat = seatStoreRepository.save(seat);
        
        // When & Then
        assertThatThrownBy(() -> reserveSeatUseCase.reserveSeat(newSeat.getId(), user.getId(), newConcert.getId(), LocalDateTime.now()))
                .isInstanceOf(UserTokenException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED);
    }

    @Test
    @DisplayName("아직 오픈하지 않은 콘서트를 예약 신청을 하면 예외가 발생한다")
    void reserve_seat_before_open_test() {
        // Given
        // 유저 및 유저토큰 생성
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), 50);
        concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        Seat seat = Seat.builder()
                .seatNumber(1)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        Seat newSeat = seatStoreRepository.save(seat);

        // When & Then
        assertThatThrownBy(() -> reserveSeatUseCase.reserveSeat(newSeat.getId(), user.getId(), newConcert.getId(), LocalDateTime.now()))
                .isInstanceOf(ConcertScheduleException.class)
                .hasFieldOrPropertyWithValue("errorCode", INVALID_RESERVATION_DATE);
    }

    @Test
    @DisplayName("지난 콘서트를 예약 신청을 하면 예외가 발생한다")
    void reserve_seat_after_close_test() {
        // Given
        // 유저 및 유저토큰 생성
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), 50);
        concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        Seat seat = Seat.builder()
                .seatNumber(1)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        Seat newSeat = seatStoreRepository.save(seat);

        // When & Then
        assertThatThrownBy(() -> reserveSeatUseCase.reserveSeat(newSeat.getId(), user.getId(), newConcert.getId(), LocalDateTime.now()))
                .isInstanceOf(ConcertScheduleException.class)
                .hasFieldOrPropertyWithValue("errorCode", INVALID_RESERVATION_DATE);
    }

    @Test
    @DisplayName("예약이 가득찬 콘서트를 예약 신청을 하면 예외가 발생한다")
    void reserve_seat_full_test() {
        // Given
        // 유저 및 유저토큰 생성
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), 0);
        concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        Seat seat = Seat.builder()
                .seatNumber(1)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        Seat newSeat = seatStoreRepository.save(seat);

        // When & Then
        assertThatThrownBy(() -> reserveSeatUseCase.reserveSeat(newSeat.getId(), user.getId(), newConcert.getId(), LocalDateTime.now()))
                .isInstanceOf(ConcertScheduleException.class)
                .hasFieldOrPropertyWithValue("errorCode", FULL_RESERVATION_SEAT);
    }

    @Test
    @DisplayName("좌석을 예약하면 잔여 좌석이 하나 차감된다.")
    void reserve_seat_decrease_remain_of_reservation_seat() {
        // Given
        // 유저 및 유저토큰 생성
        int remainOfReservationSeat = 1;
        User user = createUserAndUserToken("test" + LocalDateTime.now(), TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), remainOfReservationSeat);
        concertScheduleStoreRepository.save(newConcertSchedule);

        // 좌석 생성
        Seat seat = Seat.builder()
                .seatNumber(1)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        Seat newSeat = seatStoreRepository.save(seat);

        // When
        reserveSeatUseCase.reserveSeat(newSeat.getId(), user.getId(), newConcert.getId(), LocalDateTime.now());

        // Then
        ConcertSchedule findConcertSchedule = concertScheduleReaderRepository.getConcertSchedule(newConcert.getId());
        assertThat(findConcertSchedule.getRemainOfReservationSeat()).isEqualTo(remainOfReservationSeat - 1);
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