package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.entities.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthenticationRepo extends JpaRepository<UserAuthentication, Long> {
    UserAuthentication findByUser(User user);

    UserAuthentication findByUser_Email(String email);

    UserAuthentication findByToken(String token);

    boolean existsByUser(User user);


}
