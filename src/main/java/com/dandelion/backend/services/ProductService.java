package com.dandelion.backend.services;

import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductResponse;
import com.dandelion.backend.payloads.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDTO createProduct(MultipartFile multipartFile, ProductBody productBody) throws IOException;

    ProductDTO updateProduct(Long productId, MultipartFile multipartFile, ProductBody productBody) throws IOException;

    void deleteProduct(Long productId) throws IOException;

    ProductDTO getProductById(Long productId);

    ProductResponse getAllProducts(String search, Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String category);

}
