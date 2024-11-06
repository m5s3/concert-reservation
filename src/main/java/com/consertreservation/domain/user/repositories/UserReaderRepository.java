package com.consertreservation.domain.user.repositories;

import com.consertreservation.domain.user.model.User;
import java.util.Optional;

public interface UserReaderRepository {
    Optional<User> getUser(Long userId);
}
