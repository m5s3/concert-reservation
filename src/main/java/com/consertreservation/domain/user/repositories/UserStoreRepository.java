package com.consertreservation.domain.user.repositories;

import com.consertreservation.domain.user.model.User;

public interface UserStoreRepository {

    User save(User user);
}
