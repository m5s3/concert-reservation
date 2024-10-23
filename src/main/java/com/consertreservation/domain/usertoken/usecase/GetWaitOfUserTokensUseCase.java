package com.consertreservation.domain.usertoken.usecase;

import com.consertreservation.domain.usertoken.usecase.dto.ResultUserTokenUseCaseDto;
import java.util.List;

public interface GetWaitOfUserTokensUseCase {
    List<ResultUserTokenUseCaseDto> execute(int count);
}
