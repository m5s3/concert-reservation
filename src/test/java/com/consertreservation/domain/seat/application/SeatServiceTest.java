package com.consertreservation.domain.seat.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleReaderRepository;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import com.consertreservation.domain.concert.repository.ConcertStoreRepository;
import com.consertreservation.domain.payment.repository.PaymentReaderRepository;
import com.consertreservation.domain.payment.repository.PaymentStoreRepository;
import com.consertreservation.domain.seat.exception.ReservationSeatException;
import com.consertreservation.domain.seat.exception.SeatException;
import com.consertreservation.domain.seat.infra.SeatReaderCustomRepository;
import com.consertreservation.domain.seat.model.ReservationSeat;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;
import com.consertreservation.domain.seat.repository.ReservationSeatReadRepository;
import com.consertreservation.domain.seat.repository.ReservationSeatStoreRepository;
import com.consertreservation.domain.seat.repository.SeatReaderRepository;
import com.consertreservation.domain.seat.repository.SeatStoreRepository;
import com.consertreservation.domain.user.infra.UserReaderCustomRepository;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StopWatch;

@ActiveProfiles("test")
@SpringBootTest
@Rollback(false)
class SeatServiceTest {

    private static final Logger log = LoggerFactory.getLogger(SeatServiceTest.class);

    @Autowired
    SeatService seatService;
    @Autowired
    UserTokenStoreRepository userTokenStoreRepository;
    @Autowired
    UserReaderCustomRepository userReaderRepository;
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
    @Autowired
    UserReaderCustomRepository userReaderCustomRepository;
    @Autowired
    PaymentStoreRepository paymentStoreRepository;
    @Autowired
    PaymentReaderRepository paymentReaderRepository;
    @Autowired
    ReservationSeatStoreRepository reservationSeatStoreRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private ReservationSeatReadRepository reservationSeatReadRepository;
    @Autowired
    private SeatReaderRepository seatReaderRepository;

    @Test
    @DisplayName("예약 좌석 동시성 테스트")
    void concurrancy_reserve_seat_test() {
        // Given
        // 유저 및 유저토큰 생성
        int countOfTask = 500;
        log.info("==== 유저 및 유저토큰 생성 시작 ====");
        for (int i = 0; i < countOfTask; i++) {
            createUserAndUserToken("test" + i , 10_000L, TokenStatus.SUCCESS);
        }
        // 콘서트 생성
        log.info("==== 콘서트 생성 시작 ====");
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        log.info("==== 콘서트 스케쥴 생성 시작 ====");
        int countOfSeat = 50;
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), countOfSeat, countOfSeat);

        // 좌석 생성
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            seats.add(createSeat(newConcertSchedule, 1_000L, SeatStatus.AVAILABLE));
        }

        // When
        log.info("==== 동시성 테스트 시작 ====");
        int expectedOfSuccess = 1;
        int expectedOfFailure = countOfTask - expectedOfSuccess;
        AtomicInteger success = new AtomicInteger();
        AtomicInteger failure = new AtomicInteger();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < countOfTask; i++) {
            int index = i;
            futures.add(CompletableFuture.runAsync(() -> {
                User user = userReaderRepository.getUserByName("test" + index).get();
                log.info("user={}", user.getName());
                try {
                    seatService.reserveSeat(seats.getFirst().getId(), user.getId(), newConcert.getId(), LocalDateTime.now());
                    success.getAndIncrement();
                } catch (ReservationSeatException | SeatException e) {
                    log.info("e={}", e.getMessage());
                    failure.getAndIncrement();
                }
            }));
        }

        StopWatch stopWatch = new StopWatch("동시성 테스트");
        stopWatch.start();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).join();
        stopWatch.stop();
        log.info("==== 동시성 테스트 종료 ====");
        // Then
        System.out.println(stopWatch.prettyPrint());
        ConcertSchedule concertSchedule = concertScheduleReaderRepository.getConcertSchedule(newConcert.getId());
        assertThat(concertSchedule.getRemainOfReservationSeat()).isEqualTo(countOfSeat - 1);
        assertThat(success.get()).isEqualTo(expectedOfSuccess);
        assertThat(failure.get()).isEqualTo(expectedOfFailure);
    }

    private Seat createSeat(ConcertSchedule newConcertSchedule, long fee, SeatStatus seatStatus) {
        Seat seat = Seat.builder()
                .seatNumber(1)
                .fee(fee)
                .status(seatStatus)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        return seatStoreRepository.save(seat);
    }

    private User createUserAndUserToken(String name, long charge, TokenStatus tokenStatus) {
        User user = User.builder()
                .name(name)
                .charge(charge)
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

    private ConcertSchedule createConcertSchedule(Concert newConcert, LocalDateTime reservationStartDate,
            LocalDateTime concertStartDate, LocalDateTime concertEndDate, int reservationSeat,
            int remainOfReservationSeat) {
        log.info("newConcert.getId()={}", newConcert.getId());
        return concertScheduleStoreRepository.save(ConcertSchedule.builder()
                .concertId(newConcert.getId())
                .reservationStartDate(reservationStartDate)
                .concertStartDate(concertStartDate)
                .concertEndDate(concertEndDate)
                .reservationSeat(reservationSeat)
                .remainOfReservationSeat(remainOfReservationSeat)
                .build());
    }

    private Concert createConcert(String title) {
        Concert concert = Concert.builder()
                .title(title)
                .build();
        return concertStoreRepository.save(concert);
    }
}