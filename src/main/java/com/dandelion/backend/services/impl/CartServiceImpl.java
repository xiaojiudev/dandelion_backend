package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Product;
import com.dandelion.backend.entities.ShoppingCart;
import com.dandelion.backend.entities.ShoppingCartItem;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.repositories.CartItemRepo;
import com.dandelion.backend.repositories.CartRepo;
import com.dandelion.backend.repositories.ProductRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public void addToCart(Long userId, AddToCartBody addToCartBody) {

        Long productId = addToCartBody.getProductId();
        Integer quantity = (addToCartBody.getQuantity() != null && addToCartBody.getQuantity() > 0)
                ? addToCartBody.getQuantity() : 1;

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
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
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepo.save(existingItem);
        } else {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setProduct(product);
            cartItem.setShoppingCart(cart);
            cartItem.setQuantity(quantity);

            cartItemRepo.save(cartItem);
        }
        
    }
}
