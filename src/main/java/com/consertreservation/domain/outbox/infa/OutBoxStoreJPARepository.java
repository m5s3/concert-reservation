package com.consertreservation.domain.outbox.infa;

import com.consertreservation.domain.outbox.model.OutBox;
import com.consertreservation.domain.outbox.repository.OutBoxStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoxStoreJPARepository extends OutBoxStoreRepository, JpaRepository<OutBox, Long> {
}
