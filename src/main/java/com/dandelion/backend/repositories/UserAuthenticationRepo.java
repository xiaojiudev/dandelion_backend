package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthenticationRepo extends JpaRepository<UserAuthentication, Long> {
    UserAuthentication findByUser(User user);

    boolean existsByUser(User user);


}
