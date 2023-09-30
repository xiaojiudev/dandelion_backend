package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.ProductDTO;
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
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    // update
    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(productId, productDTO), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) {

        productService.deleteProduct(productId);

        return new ResponseEntity<>(new ApiResponse("Product is deleted successfully", true), HttpStatus.OK);
    }

    // get all
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    // get one
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    // get by category id
    @GetMapping("/products/filter")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@RequestParam("category") Long categoryId) {
        return new ResponseEntity<>(productService.getProductsByCategory(categoryId), HttpStatus.OK);
    }

    // search
    @GetMapping("/products/search")
    public ResponseEntity<List<ProductDTO>> searchProduct(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(productService.searchProducts(keyword), HttpStatus.OK);
    }

}
