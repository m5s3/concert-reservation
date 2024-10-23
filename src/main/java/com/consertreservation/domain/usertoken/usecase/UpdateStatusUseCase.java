package com.consertreservation.domain.usertoken.usecase;

import java.util.List;
import java.util.UUID;

public interface UpdateStatusUseCase {
    void execute(List<UUID> userTokensIds, String status);
}
