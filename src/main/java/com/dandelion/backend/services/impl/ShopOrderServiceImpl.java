package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.*;
import com.dandelion.backend.entities.enumType.Order;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.ShopOrderBody;
import com.dandelion.backend.payloads.dto.CartDTO;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;
import com.dandelion.backend.repositories.*;
import com.dandelion.backend.services.CartService;
import com.dandelion.backend.services.ShopOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ShopOrderServiceImpl implements ShopOrderService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final ShopOrderRepo shopOrderRepo;
    private final ShippingMethodRepo shippingMethodRepo;
    private final CartRepo cartRepo;
    private final OrderStatusRepo orderStatusRepo;
    private final ModelMapper modelMapper;

    private final CartService cartService;

    @Override
    public ShopOrderDTO createOrder(Long userId, ShopOrderBody shopOrderBody) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        ShippingMethod existingShippingMethod = shippingMethodRepo.findById(shopOrderBody.getShippingMethodId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method not found!"));

        CartDTO userCart = cartService.getDetailCart(existingUser.getId());

        BigDecimal shippingPrice = existingShippingMethod.getPrice();
        BigDecimal merchandisePrice = userCart.getMerchandiseTotal();
        BigDecimal totalPrice = shippingPrice.add(merchandisePrice);


        Address userDefaultAddress = addressRepo.findByIsDefaultAndUser_Id(true, existingUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address default not exist, let create new one!"));

        StringBuilder userAddressBuilder = new StringBuilder();
        userAddressBuilder.append(userDefaultAddress.getAddressLine1()).append(" ");
        userAddressBuilder.append(userDefaultAddress.getAddressLine2()).append(" ");
        userAddressBuilder.append(userDefaultAddress.getDistrict()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getWard()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getCity()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getCountry()).append(", ");

        OrderStatus getStatus = orderStatusRepo.findByStatus(Order.TO_PAY)
                .orElseGet(() -> {
                    OrderStatus orderStatus = OrderStatus.builder()
                            .status(Order.TO_PAY)
                            .build();

                    orderStatusRepo.save(orderStatus);

                    return orderStatus;
                });


        ShopOrder shopOrder = ShopOrder.builder()
                .user(existingUser)
                .userComment(shopOrderBody.getUserComment())
                .userPhone(userDefaultAddress.getPhone())
                .shippingAddress(userAddressBuilder.toString())
                .shippingMethod(existingShippingMethod)
                .merchandiseTotal(merchandisePrice)
                .total(totalPrice)
                .orderStatus(getStatus)
                .trackingCode(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase())
                .build();

        shopOrderRepo.save(shopOrder);

        ShopOrderDTO shopOrderDTO = ShopOrderDTO.builder()
                .id(shopOrder.getId())
                .userComment(shopOrder.getUserComment())
                .userPhone(shopOrder.getUserPhone())
                .shippingAddress(shopOrder.getShippingAddress())
                .merchandiseTotal(shopOrder.getMerchandiseTotal())
                .shippingFee(shippingPrice)
                .total(shopOrder.getTotal())
                .trackingCode(shopOrder.getTrackingCode())
                .build();

        return shopOrderDTO;
    }

    @Override
    public ShopOrderDTO updateOrder(Long userId, Long shopOrderId) {
        return null;
    }

    @Override
    public ShopOrderDTO getOrderById(Long orderId) {
        return null;
    }

    @Override
    public void deleteOrderById(Long orderId) {

    }
}
