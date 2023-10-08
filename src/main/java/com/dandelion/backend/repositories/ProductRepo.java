package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Category;
import com.dandelion.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {


    Optional<Product> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);


    List<Product> findAllByCategory(Category category);

    List<Product> findAllByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(String keyword, String categoryKeyword);

    List<Product> findByTagContainsIgnoreCase(String tag);


}
