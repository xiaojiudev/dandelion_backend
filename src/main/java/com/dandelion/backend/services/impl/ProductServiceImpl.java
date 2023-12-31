package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Category;
import com.dandelion.backend.entities.Product;
import com.dandelion.backend.entities.ShoppingCartItem;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.ProductBody;
import com.dandelion.backend.payloads.ProductResponse;
import com.dandelion.backend.payloads.dto.ProductDTO;
import com.dandelion.backend.repositories.CartItemRepo;
import com.dandelion.backend.repositories.CartRepo;
import com.dandelion.backend.repositories.CategoryRepo;
import com.dandelion.backend.repositories.ProductRepo;
import com.dandelion.backend.services.FileUpload;
import com.dandelion.backend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private final FileUpload fileUpload;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    @Override
    public ProductDTO createProduct(MultipartFile multipartFile, ProductBody productBody) throws IOException {

        // check category exists
        Category category = categoryRepo.findByNameIgnoreCase(productBody.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + productBody.getCategory()));


        // check product name is available
        if (productRepo.existsByNameIgnoreCase(productBody.getName())) {
            throw new ResourceAlreadyExistsException("Product already exists with name = " + productBody.getName());
        }

        // Upload the files and get their respective URLs
        String mediaUrl = fileUpload.uploadFile(multipartFile);

        // Sort the media URLs alphabetically
        List<String> sortedMediaUrls = Arrays.asList(mediaUrl);
        Collections.sort(sortedMediaUrls);

        // Concatenate sorted media URLs separated by ","
        String concatenatedMediaUrls = String.join(",", sortedMediaUrls);

        // create a new product using a builder
        Product product = Product.builder()
                .name(productBody.getName())
                .weight(productBody.getWeight())
                .quantity(productBody.getQuantity())
                .price(productBody.getPrice())
                .mediaUrl(concatenatedMediaUrls)
                .description(productBody.getDescription())
                .information(productBody.getInformation())
                .tag(productBody.getTag())
                .category(category)
                .build();

        // save product
        Product newProduct = productRepo.save(product);

        // convert into DTO
        ProductDTO newProductDTO = modelMapper.map(newProduct, ProductDTO.class);
        newProductDTO.setMediaUrl(Collections.singletonList(concatenatedMediaUrls.split(",")[0]));
        newProductDTO.setCategory(category.getName());

        return newProductDTO;
    }

    @Override
    public ProductDTO updateProduct(Long productId, MultipartFile multipartFile, ProductBody productBody) throws IOException {

        // check product exist
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        // if update product_name check it available to use. Because product name is unique
        if (!existingProduct.getName().equals(productBody.getName())) {
            Optional<Product> temp = productRepo.findByNameIgnoreCase(productBody.getName());

            if (temp.isPresent()) {
                throw new ResourceAlreadyExistsException("Product name already exist, choose a difference name!");
            }

        }

        // check category exist
        Category category = categoryRepo.findByNameIgnoreCase(productBody.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category id not found: " + productBody.getCategory()));


        String mediaUrl = existingProduct.getMediaUrl();

        // Upload the file if a new one is provided
        if (multipartFile != null) {
            String publicId = fileUpload.extractPublicId(mediaUrl);
            fileUpload.deleteFile(publicId);
            mediaUrl = fileUpload.uploadFile(multipartFile);
        }

        // set properties
        existingProduct.setName(productBody.getName());
        existingProduct.setWeight(productBody.getWeight());
        existingProduct.setQuantity(productBody.getQuantity());
        existingProduct.setMediaUrl(mediaUrl);
        existingProduct.setPrice(productBody.getPrice());
        existingProduct.setDescription(productBody.getDescription());
        existingProduct.setInformation(productBody.getInformation());
        existingProduct.setTag(productBody.getTag());
        existingProduct.setCategory(category);

        // save
        Product updatedProduct = productRepo.save(existingProduct);

        // convert into DTO
        ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
        updatedProductDTO.setCategory(category.getName());

        return updatedProductDTO;
    }

    @Override
    public void deleteProduct(Long productId) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        List<ShoppingCartItem> cartItems = cartItemRepo.findByProduct_Id(productId);

        if (!cartItems.isEmpty()) {
            for (ShoppingCartItem cartItem : cartItems) {
                cartItem.setProduct(null);
            }
            cartItemRepo.saveAll(cartItems);
        }

        String mediaUrl = product.getMediaUrl();
        if (mediaUrl != null) {
            String publicId = fileUpload.extractPublicId(mediaUrl);

            fileUpload.deleteFile(publicId);
        }


        productRepo.delete(product);
    }

    @Override
    public ProductResponse getAllProducts(String search, Integer pageNumber, Integer pageSize, String sortBy, String sortDir, String category) {
        Page<Product> pageProduct;
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable paging = PageRequest.of(pageNumber, pageSize, sort);

        if (category != null && !category.isEmpty() && !category.equalsIgnoreCase("All")) {
            Category tempCategory = categoryRepo.findByNameIgnoreCase(category)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + category));

            if (search != null && !search.isEmpty()) {
                pageProduct = productRepo.findAllByNameContainsIgnoreCaseAndCategory(search, tempCategory, paging);
            } else {
                pageProduct = productRepo.findAllByCategory(tempCategory, paging);
            }
        } else if (search != null && !search.isEmpty()) {
            pageProduct = productRepo.findAllByNameContainsIgnoreCase(search, paging);
        } else {
            pageProduct = productRepo.findAll(paging);
        }

        List<Product> products = pageProduct.getContent();

        List<ProductDTO> productDTOs = products.stream()
                .map(item -> {
                    ProductDTO productDTO = new ProductDTO();

                    productDTO.setId(item.getId());
                    productDTO.setName(item.getName());
                    productDTO.setWeight(item.getWeight());
                    productDTO.setQuantity(item.getQuantity());
                    productDTO.setMediaUrl(Collections.singletonList(item.getMediaUrl().split(",")[0]));
                    productDTO.setPrice(item.getPrice());
                    productDTO.setDescription(item.getDescription());
                    productDTO.setInformation(item.getInformation());
                    productDTO.setTag(item.getTag());
                    productDTO.setCategory(item.getCategory().getName());

                    return productDTO;

                })
                .collect(Collectors.toList());

        ProductResponse productResponse = ProductResponse.builder()
                .content(productDTOs)
                .pageNumber(pageProduct.getNumber())
                .pageSize(pageProduct.getSize())
                .totalElements(pageProduct.getTotalElements())
                .totalPages(pageProduct.getTotalPages())
                .lastPage(pageProduct.isLast())
                .build();

        return productResponse;
    }

    @Override
    public ProductDTO getProductById(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id = " + productId));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setWeight(product.getWeight());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setMediaUrl(Arrays.asList(product.getMediaUrl().split(",")));
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setInformation(product.getInformation());
        productDTO.setTag(product.getTag());
        productDTO.setCategory(product.getCategory().getName());

        return productDTO;
    }

}
