package com.consertreservation.domain.user.repositories;

import com.consertreservation.domain.user.model.User;
import java.util.Optional;

public interface UserReaderRepository {
    Optional<User> getUserWithLock(Long userId);
    Optional<User> getUser(Long userId);
    Optional<User> getUserByName(String email);
}
