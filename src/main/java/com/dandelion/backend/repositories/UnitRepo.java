package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepo extends JpaRepository<Unit, Long> {

    Optional<Unit> findByNameIgnoreCase(String name);

}
