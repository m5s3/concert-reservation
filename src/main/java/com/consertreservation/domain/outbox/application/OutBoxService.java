package com.consertreservation.domain.outbox.application;

import com.consertreservation.domain.outbox.application.dto.ResultServiceOutBox;
import com.consertreservation.domain.outbox.components.OutBoxComponent;
import com.consertreservation.domain.outbox.components.dto.ResultOutBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OutBoxService {

    private final OutBoxComponent outBoxComponent;

    public ResultServiceOutBox showOutBox(Long id) {
        ResultOutBox outBox = outBoxComponent.showOutBox(id);
        return ResultServiceOutBox.builder()
                .id(outBox.id())
                .status(outBox.status())
                .payload(outBox.payload())
                .createdAt(outBox.createdAt())
                .updatedAt(outBox.updatedAt())
                .build();
    }

    public List<ResultServiceOutBox> showOutBoxs() {
        return outBoxComponent.showOutBoxes()
                .stream()
                .map(outbox -> ResultServiceOutBox.builder()
                        .id(outbox.id())
                        .status(outbox.status())
                        .payload(outbox.payload())
                        .createdAt(outbox.createdAt())
                        .updatedAt(outbox.updatedAt())
                        .build()).toList();
    }

    public void updateOutBoxToReceived(Long id) {
        outBoxComponent.updateOutBoxToReceived(id);
    }

    public void updateOutBoxToSuccess(Long id) {
        outBoxComponent.updateOutBoxToSuccess(id);
    }
}
