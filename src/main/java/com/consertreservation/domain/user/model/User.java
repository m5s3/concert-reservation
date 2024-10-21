package com.consertreservation.domain.user.model;

import static com.consertreservation.domain.user.exception.UserErrorCode.INVALID_CHARGE;
import static com.consertreservation.domain.user.exception.UserErrorCode.NOT_ENOUGH_CHARGE;

import com.consertreservation.domain.base.BaseTimeEntity;
import com.consertreservation.domain.user.exception.UserErrorCode;
import com.consertreservation.domain.user.exception.UserException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private long charge;
    private String name;

    @Builder
    public User(Long id, long charge, String name) {
        this.id = id;
        this.charge = charge;
        this.name = name;
    }

    public void minusCharge(long amount) {
        if (charge < amount) {
            throw new UserException(NOT_ENOUGH_CHARGE, "충전 금액이 충분하지 않습니다");
        }
        charge -= amount;
    }

    public void addCharge(long amount) {
        if (amount < 0) {
            throw new UserException(INVALID_CHARGE, "충전 금액은 음수일 수가 없습니다");
        }
        charge += amount;
    }
}


