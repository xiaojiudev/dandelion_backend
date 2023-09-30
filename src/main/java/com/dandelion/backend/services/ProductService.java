package com.dandelion.backend.services;

import com.dandelion.backend.payloads.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    void deleteProduct(Long productId);

    ProductDTO getProductById(Long productId);

    List<ProductDTO> getAllProducts();

    // get all product by category
    List<ProductDTO> getProductsByCategory(Long categoryId);

    // search products
    List<ProductDTO> searchProducts(String keyword);
}
