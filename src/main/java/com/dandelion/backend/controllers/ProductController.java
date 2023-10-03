package com.dandelion.backend.controllers;


import com.dandelion.backend.configs.AppConstants;
import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductResponse;
import com.dandelion.backend.payloads.dto.ProductDTO;
import com.dandelion.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    // create
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductBody productBody) {
        return new ResponseEntity<>(productService.createProduct(productBody), HttpStatus.CREATED);
    }

    // update
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody ProductBody productBody) {
        return new ResponseEntity<>(productService.updateProduct(productId, productBody), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {

        productService.deleteProduct(productId);

        return new ResponseEntity<>(new ApiResponse("Product is deleted successfully", true), HttpStatus.OK);
    }

    // get all
    @GetMapping("/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    // get one
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    // get by category id
    @GetMapping("/products/cate")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@RequestParam("category") String category) {
        return new ResponseEntity<>(productService.getProductsByCategory(category), HttpStatus.OK);
    }

    // get by category id
    @GetMapping("/products/tag")
    public ResponseEntity<List<ProductDTO>> getProductsByTag(@RequestParam("tag") String tag) {
        return new ResponseEntity<>(productService.getProductsByTag(tag), HttpStatus.OK);
    }

    // search
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(productService.searchProducts(keyword), HttpStatus.OK);
    }

}
