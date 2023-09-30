package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationRepo extends JpaRepository<Variation, Long> {
    List<Variation> findByCategory_NameIgnoreCase(String name);


}
