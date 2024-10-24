package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import com.consertreservation.domain.usertoken.components.dto.UserTokenDto;
import com.consertreservation.domain.usertoken.model.TokenStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IsAuthorizedUseCaseImpl implements IsAuthorizedUseCase{

    private final UserTokenComponent userTokenComponent;

    @Override
    public boolean execute(Long userId) {
        Optional<UserTokenDto> userToken = userTokenComponent.showUserToken(userId);
        if (userToken.isPresent()) {
            return TokenStatus.isSuccess(userToken.get().tokenStatus());
        }
        return false;
    }
}
