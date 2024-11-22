package com.consertreservation.domain.outbox.model;

import com.consertreservation.domain.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutBox extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbox_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="status", columnDefinition = "varchar(20)")
    private OutBoxStatus status;

    private String payload;

    @Builder
    public OutBox(Long id, OutBoxStatus status, String payload) {
        this.id = id;
        this.status = status;
        this.payload = payload;
    }

    public void updateStatus(OutBoxStatus status) {
        this.status = status;
    }
}
