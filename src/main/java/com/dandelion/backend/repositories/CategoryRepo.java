package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);
}
