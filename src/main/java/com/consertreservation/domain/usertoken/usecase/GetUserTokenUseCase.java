package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;

public interface GetUserTokenUseCase {
    ResultUserTokenUseCaseDto execute(Long userId);
}
