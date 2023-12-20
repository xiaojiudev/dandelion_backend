package com.dandelion.backend.controllers;


import com.dandelion.backend.configs.AppConstants;
import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductResponse;
import com.dandelion.backend.payloads.dto.ProductDTO;
import com.dandelion.backend.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    // create
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(
            @ModelAttribute ProductBody productBody,
            @RequestParam("media_file") MultipartFile mediaFile) throws IOException {
        return new ResponseEntity<>(productService.createProduct(mediaFile, productBody), HttpStatus.CREATED);
    }

    // update
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "media_file", required = false) MultipartFile mediaFile,
            @ModelAttribute ProductBody productBody) throws IOException {
        return new ResponseEntity<>(productService.updateProduct(productId, mediaFile, productBody), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") Long productId) throws IOException {

        productService.deleteProduct(productId);

        return new ResponseEntity<>(new ApiResponse("Product is deleted successfully", true), HttpStatus.OK);
    }

    // get all
    @GetMapping("/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir,
            @RequestParam(value = "category", required = false) String category
    ) {
        return new ResponseEntity<>(productService.getAllProducts(search, pageNumber, pageSize, sortBy, sortDir, category), HttpStatus.OK);
    }

    // get one
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Long productId) {
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

}
