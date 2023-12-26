package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Product;
import com.dandelion.backend.entities.ShoppingCart;
import com.dandelion.backend.entities.ShoppingCartItem;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.dto.CartDTO;
import com.dandelion.backend.payloads.dto.CartDetailDTO;
import com.dandelion.backend.repositories.CartItemRepo;
import com.dandelion.backend.repositories.CartRepo;
import com.dandelion.backend.repositories.ProductRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {


    private final UserRepo userRepo;

    private final ProductRepo productRepo;

    private final CartRepo cartRepo;

    private final CartItemRepo cartItemRepo;

    @Override
    public CartDTO addToCart(Long userId, AddToCartBody addToCartBody) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Long productId = addToCartBody.getProductId();
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        Integer quantity = (addToCartBody.getQuantity() != null && addToCartBody.getQuantity() > 0)
                ? addToCartBody.getQuantity() : 1;

        ShoppingCart cart = cartRepo.findByStatusAndUser_Id(true, user.getId())
                .orElseGet(() -> {
                    ShoppingCart createdCart = new ShoppingCart();
                    createdCart.setUser(user);
                    createdCart.setStatus(true);

                    return cartRepo.save(createdCart);
                });

        // Check if the product already exists in the cart
        Optional<ShoppingCartItem> existingItemOpt = cart.getShoppingCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        // if exist increase quantity else add this product to cart
        if (existingItemOpt.isPresent()) {
            ShoppingCartItem existingItem = existingItemOpt.get();

            if (quantity == 1) {
                existingItem.setQuantity(quantity + existingItem.getQuantity());
            } else {
                existingItem.setQuantity(quantity);
            }

            cartItemRepo.save(existingItem);
        } else {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setProduct(product);
            cartItem.setShoppingCart(cart);
            cartItem.setQuantity(quantity);

            cartItemRepo.save(cartItem);
        }

        CartDTO cartDTO = getDetailCart(userId);

        return cartDTO;
    }

    @Override
    public void removeItems(Long userId, List<Long> productIds) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        ShoppingCart cart = cartRepo.findByStatusAndUser_Id(true, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!"));

        for (Long productId : productIds) {
            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
            ShoppingCartItem cartItem = cartItemRepo.findByShoppingCart_IdAndProduct_Id(cart.getId(), product.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

            cartItemRepo.delete(cartItem);
        }

    }

    @Override
    public CartDTO getDetailCart(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        ShoppingCart userCart = cartRepo.findByStatusAndUser_Id(true, user.getId())
                .orElseGet(() -> {
                    ShoppingCart createdCart = new ShoppingCart();
                    createdCart.setUser(user);
                    createdCart.setStatus(true);

                    return cartRepo.save(createdCart);
                });

        // Calculate Merchandise Subtotal
        AtomicReference<BigDecimal> merchandiseTotal = new AtomicReference<>(BigDecimal.ZERO);

        List<ShoppingCartItem> cartItems = cartItemRepo.findByShoppingCart_Id(userCart.getId());

        List<CartDetailDTO> cartDetailDTOs = cartItems.stream()
                .map(item -> {
                    CartDetailDTO tempItem = new CartDetailDTO();
                    Product tempProduct = item.getProduct();

                    Integer quantity = item.getQuantity();
                    BigDecimal price = tempProduct.getPrice();
                    BigDecimal itemSubTotal = new BigDecimal(quantity).multiply(price);

                    // Update totalFee using AtomicReference
                    merchandiseTotal.updateAndGet(oldTotal -> oldTotal.add(itemSubTotal));

                    tempItem.setProductId(tempProduct.getId());
                    tempItem.setName(tempProduct.getName());
                    tempItem.setMediaUrl(tempProduct.getMediaUrl().split(",")[0]);
                    tempItem.setUnitPrice(price);
                    tempItem.setDescription(tempProduct.getDescription());
                    tempItem.setInformation(tempProduct.getInformation());
                    tempItem.setQuantity(quantity);
                    tempItem.setAvailableQuantity(tempProduct.getQuantity());
                    tempItem.setItemSubTotal(itemSubTotal);

                    return tempItem;
                })
                .collect(Collectors.toList());

        CartDTO cartDTO = new CartDTO();

        cartDTO.setCartId(userCart.getId());
        cartDTO.setUserId(user.getId());
        cartDTO.setStatus(userCart.getStatus());
        cartDTO.setMerchandiseTotal(merchandiseTotal.get()); // Get the totalFee value from AtomicReference
        cartDTO.setItems(cartDetailDTOs);

        return cartDTO;
    }


}
