package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalUserDAO extends JpaRepository<LocalUser, Long> {

    // create free method like save, findById etc
    Optional<LocalUser> findByEmailIgnoreCase(String email);

    Optional<LocalUser> findByPhoneIgnoreCase(String phone);
}
