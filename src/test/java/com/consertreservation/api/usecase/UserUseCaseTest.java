package com.consertreservation.api.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.consertreservation.domain.user.components.dto.UserChargeDto;
import com.consertreservation.domain.user.infra.UserReaderCustomRepository;
import com.consertreservation.domain.user.model.User;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserUseCaseTest {

    @Autowired
    UserUseCase userUseCase;

    @Autowired
    UserReaderCustomRepository userReaderCustomRepository;

    @Test
    @DisplayName("충전하기")
    void charge() {
        // Given
        User user = userReaderCustomRepository.getUser(1L).get();
        long nowCharge = user.getCharge();

        // When
        userUseCase.charge(user.getId(), 10000);

        // Then
        UserChargeDto charge = userUseCase.getCharge(1L);
        assertThat(charge.amount()).isEqualTo(nowCharge + 10000);
    }
}