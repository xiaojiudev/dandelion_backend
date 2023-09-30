package com.dandelion.backend.services;

import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductBody productBody);

    ProductDTO updateProduct(Long productId, ProductBody productBody);

    void deleteProduct(Long productId);

    ProductDTO getProductById(Long productId);

    List<ProductDTO> getAllProducts();

    // get all product by category
    List<ProductDTO> getProductsByCategory(String category);

    // search products
    List<ProductDTO> searchProducts(String keyword);
}
