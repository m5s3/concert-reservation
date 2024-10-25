package com.consertreservation.domain.user.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.consertreservation.domain.user.application.dto.ResultChargeServiceDto;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStoreRepository userStoreRepository;

    @Test
    @DisplayName("충전 동시성 테스트")
    void concurrency_charge_test() throws InterruptedException {
        // Given
        int numberOfThreads = 5;

        User user = userStoreRepository.save(User.builder().name("test").charge(0).build());

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // When
        service.execute(() -> {
            userService.charge(1L, 10_000L);
            latch.countDown();
        });
        service.execute(() -> {
            userService.charge(1L, 20_000L);
            latch.countDown();
        });
        service.execute(() -> {
            userService.charge(1L, 30_000L);
            latch.countDown();
        });
        service.execute(() -> {
            userService.charge(1L, 40_000L);
            latch.countDown();
        });
        service.execute(() -> {
            userService.charge(1L, 50_000L);
            latch.countDown();
        });
        latch.await();

        // Then
        ResultChargeServiceDto result = userService.getCharge(user.getId());
        assertThat(result.amount()).isEqualTo(150_000L);
    }
}