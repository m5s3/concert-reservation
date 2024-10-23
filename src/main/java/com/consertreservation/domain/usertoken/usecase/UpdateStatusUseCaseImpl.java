package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.components.UserTokenComponent;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateStatusUseCaseImpl implements UpdateStatusUseCase {

    private final UserTokenComponent userTokenComponent;

    @Override
    public void execute(List<UUID> userTokenIds, String status) {
        userTokenComponent.updateUserTokensStatus(userTokenIds, status);
    }
}
