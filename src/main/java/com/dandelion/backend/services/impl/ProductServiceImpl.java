package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Category;
import com.dandelion.backend.entities.Product;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.dto.ProductDTO;
import com.dandelion.backend.repositories.CategoryRepo;
import com.dandelion.backend.repositories.ProductRepo;
import com.dandelion.backend.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO createProduct(ProductBody productBody) {

        // check category exists
        Category category = categoryRepo.findById(productBody.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + productBody.getCategoryId()));

        // check product name is available
        Optional<Product> tempProduct = productRepo.findByNameIgnoreCase(productBody.getName());

        if (tempProduct.isPresent()) {
            throw new ResourceAlreadyExistsException("Product already exist with name = " + productBody.getName());
        }

        // convert into product
        Product product = modelMapper.map(productBody, Product.class);

        // set category for product
        product.setCategory(category);

        // save product
        Product newProduct = productRepo.save(product);

        // convert into DTO
        ProductDTO newProductDTO = modelMapper.map(newProduct, ProductDTO.class);
        newProductDTO.setCategory(category.getName());

        return newProductDTO;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductBody productBody) {

        // check product exist
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        // if update product_name check it available to use. Because product name is unique
        if (!product.getName().equals(productBody.getName())) {
            Optional<Product> temp = productRepo.findByNameIgnoreCase(productBody.getName());

            if (temp.isPresent()) {
                throw new ResourceAlreadyExistsException("Product name already exist, choose a difference name!");
            }

        }

        // check category exist
        Category category = categoryRepo.findById(productBody.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category id not found: " + productBody.getCategoryId()));

        // set properties
        product.setName(productBody.getName());
        product.setWeight(productBody.getWeight());
        product.setQuantity(productBody.getQuantity());
        product.setMediaUrl(productBody.getMediaUrl());
        product.setPrice(productBody.getPrice());
        product.setDescription(productBody.getDescription());
        product.setInformation(productBody.getInformation());
        product.setTag(productBody.getTag());
        product.setCategory(category);

        // save
        Product updatedProduct = productRepo.save(product);

        // convert into DTO
        ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
        updatedProductDTO.setCategory(category.getName());

        return updatedProductDTO;
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        productRepo.delete(product);

    }

    @Override
    public List<ProductDTO> getAllProducts() {

        List<Product> products = productRepo.findAll();

        List<ProductDTO> productDTOs = products.stream()
                .map(item -> {
                    ProductDTO productDTO = modelMapper.map(item, ProductDTO.class);
                    productDTO.setCategory(item.getCategory().getName());

                    return productDTO;

                })
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Override
    public ProductDTO getProductById(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        productDTO.setCategory(product.getCategory().getName());

        return productDTO;
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {

        Category tempCate = categoryRepo.findByNameIgnoreCase(category)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + category));

        List<Product> products = productRepo.findAllByCategory(tempCate);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found!");
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(item -> modelMapper.map(item, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Override
    public List<ProductDTO> getProductsByTag(String tag) {
        List<Product> products = productRepo.findByTagContainsIgnoreCase(tag);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Products not found!");
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(item -> modelMapper.map(item, ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOs;
    }

    @Override
    public List<ProductDTO> searchProducts(String keyword) {

        List<Product> matchingProducts = productRepo.findAllByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(keyword, keyword);

        if (matchingProducts.isEmpty()) {
            throw new ResourceNotFoundException("Product not found!");
        }

        List<ProductDTO> matchingProductsDTOs = matchingProducts.stream()
                .map(item -> modelMapper.map(item, ProductDTO.class))
                .collect(Collectors.toList());

        return matchingProductsDTOs;
    }
}
