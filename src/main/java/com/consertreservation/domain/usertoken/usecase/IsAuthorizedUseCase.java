package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.model.UserToken;

public interface IsAuthorizedUseCase {

    boolean execute(Long userId);
}
