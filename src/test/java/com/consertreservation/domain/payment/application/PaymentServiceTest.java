package com.consertreservation.domain.payment.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleReaderRepository;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import com.consertreservation.domain.concert.repository.ConcertStoreRepository;
import com.consertreservation.domain.payment.application.dto.ResultPaymentServiceDto;
import com.consertreservation.domain.payment.model.Payment;
import com.consertreservation.domain.payment.repository.PaymentReaderRepository;
import com.consertreservation.domain.payment.repository.PaymentStoreRepository;
import com.consertreservation.domain.seat.model.ReservationSeat;
import com.consertreservation.domain.seat.model.ReservationSeatStatus;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;
import com.consertreservation.domain.seat.repository.ReservationSeatStoreRepository;
import com.consertreservation.domain.seat.repository.SeatStoreRepository;
import com.consertreservation.domain.user.infra.UserReaderCustomRepository;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PaymentServiceTest {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceTest.class);
    @Autowired
    PaymentService paymentService;
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
    @Autowired
    UserReaderCustomRepository userReaderCustomRepository;
    @Autowired
    PaymentStoreRepository paymentStoreRepository;
    @Autowired
    PaymentReaderRepository paymentReaderRepository;
    @Autowired
    ReservationSeatStoreRepository reservationSeatStoreRepository;

    @Test
    @DisplayName("결제 동시성 테스트")
    void concurrency_pay_test() throws InterruptedException {
        // Given
        // 유저 및 유저토큰 생성
        User user = createUserAndUserToken("test" + LocalDateTime.now(), 10_000L, TokenStatus.SUCCESS);

        // 콘서트 생성
        Concert newConcert = createConcert("test title");

        // 콘서트 스케줄 생성
        ConcertSchedule newConcertSchedule = createConcertSchedule(newConcert, LocalDateTime.now().minusDays(3),
                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), 50);

        // 좌석 생성
        Seat seat = createSeat(newConcertSchedule, 1_000L, SeatStatus.AVAILABLE);
        
        // 예약 좌석 생성
        createReservationSeat(seat, user, ReservationSeatStatus.ING);

        // When & Then
        int numTasks = 5;
        int numberOfThreads = 5;
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        CountDownLatch countDownLatch = new CountDownLatch(numTasks);
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        service.submit(() -> {
            ResultPaymentServiceDto pay = paymentService.pay(user.getId(), seat.getId(), 1_000L);
            System.out.println("pay=" + pay);
            countDownLatch.countDown();
        });
        service.submit(() -> {
            ResultPaymentServiceDto pay = paymentService.pay(user.getId(), seat.getId(), 1_000L);
            System.out.println("pay=" + pay);
            countDownLatch.countDown();
        });
        service.submit(() -> {
            ResultPaymentServiceDto pay = paymentService.pay(user.getId(), seat.getId(), 1_000L);
            System.out.println("pay=" + pay);
            countDownLatch.countDown();
        });
        service.submit(() -> {
            ResultPaymentServiceDto pay = paymentService.pay(user.getId(), seat.getId(), 1_000L);
            System.out.println("pay=" + pay);
            countDownLatch.countDown();
        });
        service.submit(() -> {
            ResultPaymentServiceDto pay = paymentService.pay(user.getId(), seat.getId(), 1_000L);
            System.out.println("pay=" + pay);
            countDownLatch.countDown();
        });
        countDownLatch.await();

        User findUser = userReaderCustomRepository.getUserWithLock(user.getId()).get();
        List<Payment> findPayments = paymentReaderRepository.showPayments(findUser.getId());

        assertThat(findUser.getCharge()).isEqualTo(9_000L);
        assertThat(findPayments.size()).isEqualTo(1);
    }

    private ReservationSeat createReservationSeat(Seat seat, User user, ReservationSeatStatus reservationSeatStatus) {
        ReservationSeat reservationSeat = ReservationSeat.builder()
                .seatId(seat.getId())
                .userId(user.getId())
                .status(reservationSeatStatus)
                .build();
        return reservationSeatStoreRepository.save(reservationSeat);
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
            LocalDateTime concertStartDate, LocalDateTime concertEndDate, int remainOfReservationSeat) {
        return concertScheduleStoreRepository.save(ConcertSchedule.builder()
                .concertId(newConcert.getId())
                .reservationStartDate(reservationStartDate)
                .concertStartDate(concertStartDate)
                .concertEndDate(concertEndDate)
                .remainOfReservationSeat(remainOfReservationSeat)
                .build());
    }

    private Concert createConcert(String title) {
        Concert concert = Concert.builder()
                .title(title)
                .build();
        return concertStoreRepository.save(concert);
    }

    static class CallableEx implements Callable<String> {
        private int idx;

        CallableEx(int idx) {
            this.idx = idx;
        }

        @Override
        public String call() throws Exception {

            return "test";
        }
    }
}