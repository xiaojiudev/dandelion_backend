package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    // create free method like save, findById etc
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByPhoneIgnoreCase(String phone);
}
