package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import java.util.List;

public interface GetSuccessOfUserTokensUseCase {
    List<ResultUserTokenUseCaseDto> execute(int count);
}
