package com.consertreservation.api.usecase;

import static com.consertreservation.domain.usertoken.exception.UserTokenErrorCode.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.exception.UserTokenException;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.respositories.UserTokenReaderRepository;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeatUserCaseTest {

    @Autowired
    SeatUserCase seatUserCase;

    @Autowired
    UserTokenStoreRepository userTokenStoreRepository;

    @Autowired
    UserTokenReaderRepository userTokenReaderRepository;

    @Test
    @DisplayName("권한이 없는 유저가 이용가능한 좌석을 조회를 하면 예외가 발생한다")
    void get_available_seat_unauthorized_test() {
        // Given
        long userId = 1L;
        // 유저토큰 생성
        UserToken newUserToken = UserToken.builder()
                .id(UUID.randomUUID())
                .userid(userId)
                .status(TokenStatus.WAIT)
                .build();
        userTokenStoreRepository.save(newUserToken);
        long concertScheduleId = 1L;

        // When & Then
        assertThatThrownBy(() -> seatUserCase.getAvailableSeats(userId, concertScheduleId))
                .isInstanceOf(UserTokenException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED);
    }

    @Test
    @DisplayName("권한이 있는 유저는 이용가능한 좌석을 조회 할 수가 있다")
    void get_available_seat_authorized_test() {
        // Given
        long userId = 1L;

        // 유저토큰 생성
        UserToken newUserToken = UserToken.builder()
                .id(UUID.randomUUID())
                .userid(userId)
                .status(TokenStatus.SUCCESS)
                .build();
        userTokenStoreRepository.save(newUserToken);

        long concertScheduleId = 1L;

        // When & Then
        assertThatThrownBy(() -> seatUserCase.getAvailableSeats(userId, concertScheduleId))
                .isInstanceOf(UserTokenException.class)
                .hasFieldOrPropertyWithValue("errorCode", UNAUTHORIZED);
    }
}