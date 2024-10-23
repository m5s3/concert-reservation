package com.consertreservation.api.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import com.consertreservation.domain.user.application.UserService;
import com.consertreservation.domain.user.application.dto.ResultChargeServiceDto;
import com.consertreservation.domain.user.components.dto.UserChargeDto;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserUseCaseTest {

    @Autowired
    UserService userService;

    @Autowired
    UserStoreRepository userStoreRepository;

    @Test
    @DisplayName("충전하기")
    void charge() {
        // Given
        User user = User.builder()
                .name("test")
                .build();
        User newUser = userStoreRepository.save(user);
        long nowCharge = newUser.getCharge();

        // When
        userService.charge(user.getId(), 10000L);

        // Then
        ResultChargeServiceDto charge = userService.getCharge(1L);
        assertThat(charge.amount()).isEqualTo(nowCharge + 10000);
    }
}