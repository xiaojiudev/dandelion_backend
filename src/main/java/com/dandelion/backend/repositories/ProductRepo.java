package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.Category;
import com.dandelion.backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {


    Optional<Product> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);


    List<Product> findAllByCategory(Category category);

    Page<Product> findAllByCategory(Category category, Pageable pageable);

    List<Product> findAllByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(String keyword, String categoryKeyword);

    Page<Product> findAllByNameContainsIgnoreCase(String name, Pageable pageable);

    Page<Product> findAllByNameContainsIgnoreCaseAndCategory(String name, Category category, Pageable pageable);


    List<Product> findByTagContainsIgnoreCase(String tag);


}
