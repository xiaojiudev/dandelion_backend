package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ProductDTO;
import com.dandelion.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // delete

    // get all

    // get one

    // search
}
