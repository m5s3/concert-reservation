package com.consertreservation.domain.outbox.components;

import com.consertreservation.domain.outbox.components.dto.ResultOutBox;
import com.consertreservation.domain.outbox.exception.OutBoxErrorCode;
import com.consertreservation.domain.outbox.exception.OutBoxException;
import com.consertreservation.domain.outbox.model.OutBox;
import com.consertreservation.domain.outbox.model.OutBoxStatus;
import com.consertreservation.domain.outbox.repository.OutBoxReaderRepository;
import com.consertreservation.domain.outbox.repository.OutBoxStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@RequiredArgsConstructor
public class OutBoxComponent {

    private final OutBoxStoreRepository outBoxStoreRepository;
    private final OutBoxReaderRepository outBoxReaderRepository;

    public ResultOutBox create(String payload) {
        OutBox outbox = OutBox.builder()
                .status(OutBoxStatus.SUCCESS)
                .payload(payload)
                .build();
        return ResultOutBox.fromEntity(outBoxStoreRepository.save(outbox));
    }

    @Transactional(readOnly = true)
    public ResultOutBox showOutBox(Long id) {
        OutBox findOutBox = outBoxReaderRepository.getOutBox(id).orElseThrow(() -> new OutBoxException(OutBoxErrorCode.NOT_FOUND,
                "아웃박스를 찾을 수 없습니다"));
        return ResultOutBox.fromEntity(findOutBox);
    }

    @Transactional(readOnly = true)
    public List<ResultOutBox> showOutBoxes() {
        return outBoxReaderRepository.getOutBoxesByStatus(OutBoxStatus.INIT)
                .stream()
                .map(ResultOutBox::fromEntity)
                .toList();
    }

    public void updateOutBoxToReceived(Long id) {
        OutBox findOutBox = outBoxReaderRepository.getOutBox(id).orElseThrow(() -> new OutBoxException(OutBoxErrorCode.NOT_FOUND,
                "아웃박스를 찾을 수 없습니다"));
        findOutBox.updateStatus(OutBoxStatus.RECEIVED);
    }

    public void updateOutBoxToSuccess(Long id) {
        OutBox findOutBox = outBoxReaderRepository.getOutBox(id).orElseThrow(() -> new OutBoxException(OutBoxErrorCode.NOT_FOUND,
                "아웃박스를 찾을 수 없습니다"));
        findOutBox.updateStatus(OutBoxStatus.SUCCESS);
    }
}
