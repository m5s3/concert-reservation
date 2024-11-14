package com.consertreservation.test;

import com.consertreservation.domain.concert.model.Concert;
import com.consertreservation.domain.concert.model.ConcertSchedule;
import com.consertreservation.domain.concert.repository.ConcertScheduleStoreRepository;
import com.consertreservation.domain.concert.repository.ConcertStoreRepository;
import com.consertreservation.domain.seat.exception.ReservationSeatException;
import com.consertreservation.domain.seat.exception.SeatException;
import com.consertreservation.domain.seat.model.Seat;
import com.consertreservation.domain.seat.model.SeatStatus;
import com.consertreservation.domain.seat.repository.SeatStoreRepository;
import com.consertreservation.domain.user.model.User;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Rollback(false)
class CreateMockData {

    private static final Logger log = LoggerFactory.getLogger(CreateMockData.class);
    /**
     *   public DataSource dataSource() {
     *     DriverManagerDataSource dataSource = new DriverManagerDataSource();
     *     dataSource.setUsername("root");
     *     dataSource.setPassword("root");
     *     dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
     *     dataSource.setUrl("jdbc:mariadb://localhost:3306/test_db");
     *     return dataSource;
     *   }
     */
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Autowired
    ConcertStoreRepository concertStoreRepository;
    @Autowired
    ConcertScheduleStoreRepository concertScheduleStoreRepository;
    @Autowired
    SeatStoreRepository seatStoreRepository;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("sa");
        dataSource.setPassword("1234");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/concert_reservation?useUnicode=yes&characterEncoding=UTF-8&rewriteBatchedStatements=true");
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    void createMockData() {
        int dataOfCount = 25_000;
        Instant tenDaysLater = Instant.now().plus(Duration.ofDays(10));
        Instant thirtyDaysLater = Instant.now().plus(Duration.ofDays(30));
        Instant fourthDaysLater = Instant.now().plus(Duration.ofDays(40));
        Instant fifthDaysLayer = Instant.now().plus(Duration.ofDays(50));
        int countOfSeat = 50;
        String insertConcertSql = "INSERT INTO concert (title, created_at, updated_at) VALUES (?, ?, ?)";
        String insertConcertScheduleSql =
                "INSERT INTO concert_schedule(concert_id, reservation_start_date, concert_start_date, concert_end_date,"
                        + " reservation_seat, remain_of_reservation_seat, created_at, updated_at) "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        String insertSeatSql = "INSERT INTO seat (seat_number, fee, status, concert_schedule_id, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Long firstPK = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);
        for (int j = 0; j < 1_000_000 / 25_000; j++) {
            List<Concert> concerts = new ArrayList<>();
            List<ConcertSchedule> concertSchedules = new ArrayList<>();
            List<Seat> seats = new ArrayList<>();
            for (int i = j * dataOfCount; i <= dataOfCount * (j + 1); i++) {
                Concert concert = createConcert(firstPK + i, "test concert" + i);
                ConcertSchedule concertSchedule = createConcertSchedule(firstPK + i, concert, between(tenDaysLater, thirtyDaysLater),
                        between(thirtyDaysLater, fourthDaysLater), between(fourthDaysLater, fifthDaysLayer), countOfSeat,
                        countOfSeat);
                concerts.add(concert);
                concertSchedules.add(concertSchedule);
                for (int k = 1; k <= countOfSeat; k++) {
                    seats.add(createSeat(concertSchedule,5000,SeatStatus.AVAILABLE, k));
                }
            }
            jdbcTemplate.batchUpdate(insertConcertSql,
                    concerts,
                    concerts.size(),
                    (PreparedStatement ps, Concert concert) -> {
                        ps.setString(1, concert.getTitle());
                        ps.setString(2, LocalDateTime.now().toString());
                        ps.setString(3, LocalDateTime.now().toString());
                    });

            jdbcTemplate.batchUpdate(insertConcertScheduleSql,
                    concertSchedules,
                    concertSchedules.size(),
                    (PreparedStatement ps, ConcertSchedule concertSchedule) -> {
                        ps.setLong(1, concertSchedule.getConcertId());
                        ps.setString(2, concertSchedule.getReservationStartDate().toString());
                        ps.setString(3, concertSchedule.getConcertStartDate().toString());
                        ps.setString(4, concertSchedule.getConcertEndDate().toString());
                        ps.setInt(5, concertSchedule.getReservationSeat());
                        ps.setInt(6, concertSchedule.getRemainOfReservationSeat());
                        ps.setString(7, LocalDateTime.now().toString());
                        ps.setString(8, LocalDateTime.now().toString());
                    });

            jdbcTemplate.batchUpdate(insertSeatSql,
                    seats,
                    seats.size(),
                    (PreparedStatement ps, Seat seat) -> {
                        ps.setInt(1, seat.getSeatNumber());
                        ps.setLong(2, seat.getFee());
                        ps.setString(3, seat.getStatus().toString());
                        ps.setLong(4, seat.getConcertScheduleId());
                        ps.setString(5, LocalDateTime.now().toString());
                        ps.setString(6, LocalDateTime.now().toString());
                    });
        }
    }

    private Seat createSeat(ConcertSchedule newConcertSchedule, long fee, SeatStatus seatStatus, int seatNumber) {
        Seat seat = Seat.builder()
                .seatNumber(seatNumber)
                .fee(fee)
                .status(seatStatus)
                .concertScheduleId(newConcertSchedule.getId())
                .build();
        //return seatStoreRepository.save(seat);
        return seat;
    }

    private ConcertSchedule createConcertSchedule(long id, Concert newConcert, LocalDateTime reservationStartDate,
            LocalDateTime concertStartDate, LocalDateTime concertEndDate, int reservationSeat,
            int remainOfReservationSeat) {
//        return concertScheduleStoreRepository.save(ConcertSchedule.builder()
//                .concertId(newConcert.getId())
//                .reservationStartDate(reservationStartDate)
//                .concertStartDate(concertStartDate)
//                .concertEndDate(concertEndDate)
//                .reservationSeat(reservationSeat)
//                .remainOfReservationSeat(remainOfReservationSeat)
//                .build());
        return ConcertSchedule.builder()
                .id(id)
                .concertId(newConcert.getId())
                .reservationStartDate(reservationStartDate)
                .concertStartDate(concertStartDate)
                .concertEndDate(concertEndDate)
                .reservationSeat(reservationSeat)
                .remainOfReservationSeat(remainOfReservationSeat)
                .build();
    }

    private Concert createConcert(long id, String title) {
        Concert concert = Concert.builder()
                .id(id)
                .title(title)
                .build();
//        return concertStoreRepository.save(concert);
        return concert;
    }

    public static LocalDateTime between(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(random), ZoneId.systemDefault());
    }

    public static SeatStatus createRandomSeatStatus() {
        int randomNumber = ThreadLocalRandom.current().nextInt(0, SeatStatus.values().length);
        return SeatStatus.values()[randomNumber];
    }
}
