package com.consertreservation.domain.usertoken.components;

import static org.junit.jupiter.api.Assertions.*;

import com.consertreservation.domain.usertoken.components.dto.CreateUserTokenDto;
import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import com.consertreservation.domain.usertoken.model.UserToken;
import com.consertreservation.domain.usertoken.model.UserToken.UserTokenBuilder;
import com.consertreservation.domain.usertoken.respositories.UserTokenReaderRepository;
import com.consertreservation.domain.usertoken.respositories.UserTokenStoreRepository;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserTokenComponentTest {

    @Mock
    UserTokenStoreRepository userTokenStoreRepository;
    @Mock
    UserTokenReaderRepository userTokenReaderRepository;
    @InjectMocks
    UserTokenComponent userTokenComponent;

    @Test
    @DisplayName("유저 토큰 생성")
    void create_userToken_test() {
        // Given
        long userId = 1L;
        UUID id = UUID.randomUUID();
        UserToken userToken = createUserToken(id, userId, TokenStatus.WAIT);
        Mockito.when(userTokenStoreRepository.save(Mockito.any(UserToken.class))).thenReturn(userToken);
        // When & Then
        CreateUserTokenDto newUserToken = userTokenComponent.createToken(userId);
        Assertions.assertThat(newUserToken.userId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("유저토큰 조회")
    void show_userToken_test() {
        // Given
        long userId = 1L;
        UUID id = UUID.randomUUID();
        UserToken userToken = createUserToken(id, userId, TokenStatus.WAIT);
        Mockito.when(userTokenReaderRepository.getUserToken(userId)).thenReturn(userToken);

        // When
        UserTokenDto userTokenDto = userTokenComponent.showUserToken(userId);

        // Then
        Assertions.assertThat(userTokenDto.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("유저토큰이 존재하지 않으면 생성해서 반환한다.")
    void show_userToken_when_non_exist_test() {
        // Given
        long userId = 1L;
        UUID id = UUID.randomUUID();
        UserToken userToken = createUserToken(id, userId, TokenStatus.WAIT);

        Mockito.when(userTokenReaderRepository.getUserToken(userId)).thenReturn(null);
        Mockito.when(userTokenStoreRepository.save(Mockito.any(UserToken.class))).thenReturn(userToken);
        // When
        UserTokenDto userTokenDto = userTokenComponent.showUserToken(userId);
        // Then
        Assertions.assertThat(userTokenDto.id()).isEqualTo(id);
    }

    @Test
    @DisplayName("유저토큰을 생성시 다음 순번의 대기순서를 받는다.")
    void next_waitingOrder_test() {
        // Given
        long userId = 3L;
        UUID id = UUID.randomUUID();
        UserToken thirdUserToken = createUserToken(id, userId, TokenStatus.WAIT);

        Mockito.when(userTokenStoreRepository.save(Mockito.any(UserToken.class))).thenReturn(thirdUserToken);
        Mockito.when(userTokenReaderRepository.getWaitOfUserTokenCount()).thenReturn(1L);

        // When
        UserTokenDto userTokenDto = userTokenComponent.showUserToken(userId);

        // Then
        Assertions.assertThat(userTokenDto.waitingOrder()).isEqualTo(2);
    }


    private UserToken createUserToken(UUID id, long userId, TokenStatus status) {
        return UserToken.builder()
                .id(id)
                .userid(userId)
                .status(status)
                .build();
    }
}