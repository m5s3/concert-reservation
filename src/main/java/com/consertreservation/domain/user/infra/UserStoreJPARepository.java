package com.consertreservation.domain.user.infra;

import com.consertreservation.domain.user.model.User;
import com.consertreservation.domain.user.repositories.UserStoreRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStoreJPARepository extends JpaRepository<User, Long>, UserStoreRepository {

}
