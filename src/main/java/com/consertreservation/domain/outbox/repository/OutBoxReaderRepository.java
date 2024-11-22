package com.consertreservation.domain.outbox.repository;

import com.consertreservation.domain.outbox.model.OutBox;
import com.consertreservation.domain.outbox.model.OutBoxStatus;

import java.util.List;
import java.util.Optional;

public interface OutBoxReaderRepository {

    List<OutBox> getOutBoxesByStatus(OutBoxStatus status);
    Optional<OutBox> getOutBox(Long id);
}
