package com.dandelion.backend.services;

import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductResponse;
import com.dandelion.backend.payloads.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDTO createProduct(MultipartFile multipartFile, ProductBody productBody) throws IOException;

    ProductDTO updateProduct(Long productId, ProductBody productBody);

    void deleteProduct(Long productId);

    ProductDTO getProductById(Long productId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // get all products by category
    List<ProductDTO> getProductsByCategory(String category);

    // get all products by tag
    List<ProductDTO> getProductsByTag(String tag);

    // search products
    List<ProductDTO> searchProducts(String keyword);
}
