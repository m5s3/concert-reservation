package com.consertreservation.domain.user.components;

import static com.consertreservation.domain.user.exception.UserErrorCode.NOT_FOUND;

import com.consertreservation.domain.user.components.dto.UserChargeDto;
import com.consertreservation.domain.user.exception.UserException;
import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserReaderRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserComponent {

    private final UserReaderRepository userReaderRepository;

    public void spendFee(Long userId, long amount) {
        User user = userReaderRepository.getUser(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND, "유저를 찾을 수 없습니다"));
        user.minusCharge(amount);
    }

    @Transactional(readOnly = true)
    public UserChargeDto getCharge(Long userId) {
        User user = userReaderRepository.getUser(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND, "유저를 찾을 수 없습니다"));
        return UserChargeDto.from(user);
    }

    public UserChargeDto charge(Long userId, long amount) {
        User user = userReaderRepository.getUser(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND, "유저를 찾을 수 없습니다"));;
        user.addCharge(amount);
        return UserChargeDto.from(user);
    }
}
