package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.VariationOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariationOptionRepo extends JpaRepository<VariationOption, Long> {
    List<VariationOption> findByVariation_NameIgnoreCase(String name);

    List<VariationOption> findByVariation_Category_NameIgnoreCase(String name);

    List<VariationOption> findByVariation_NameIgnoreCaseAndVariation_Category_NameIgnoreCase(String name, String name1);


}
