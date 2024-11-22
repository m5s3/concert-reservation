package com.consertreservation.domain.outbox.repository;

import com.consertreservation.domain.outbox.model.OutBox;

public interface OutBoxStoreRepository {

    OutBox save(OutBox outBox);
}
