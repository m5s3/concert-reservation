package com.consertreservation.domain.outbox.application.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ResultServiceOutBox(
        Long id,
        String status,
        String payload,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
