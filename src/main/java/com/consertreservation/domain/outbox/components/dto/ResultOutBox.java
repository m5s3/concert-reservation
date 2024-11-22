package com.consertreservation.domain.outbox.components.dto;

import com.consertreservation.domain.outbox.model.OutBox;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultOutBox(
        Long id,
        String status,
        String payload,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static ResultOutBox fromEntity(OutBox outBox) {
        return ResultOutBox.builder()
                .id(outBox.getId())
                .payload(outBox.getPayload())
                .status(outBox.getStatus().name())
                .createdAt(outBox.getCreatedAt())
                .updatedAt(outBox.getUpdatedAt())
                .build();
    }
}
