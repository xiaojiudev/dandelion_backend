package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.*;
import com.dandelion.backend.entities.enumType.Order;
import com.dandelion.backend.entities.enumType.TransactionStatus;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;
import com.dandelion.backend.payloads.dto.ShopOrderDetailDTO;
import com.dandelion.backend.repositories.*;
import com.dandelion.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final OrderDetailRepo orderDetailRepo;
    private final UserRepo userRepo;
    private final ShippingMethodRepo shippingMethodRepo;
    private final PaymentMethodRepo paymentMethodRepo;
    private final AddressRepo addressRepo;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final OrderStatusRepo orderStatusRepo;
    private final OrderTransactionRepo orderTransactionRepo;

    @Override
    public void placeOrder(Long userId, OrderRequest orderRequest) {

        // User
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        String userFullName = user.getFullName();
        String userPhone = user.getPhone();
        Address userDefaultAddress = addressRepo.findByIsDefaultAndUser_Id(true, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address default not exist, let create new one!"));

        // User Cart
        ShoppingCart userCart = cartRepo.findByStatusAndUser(true, user);
        if (userCart == null) {
            throw new ResourceNotFoundException("Shopping cart not found for the user.");
        }

        // Create Address
        StringBuilder userAddressBuilder = new StringBuilder();
        userAddressBuilder.append(userDefaultAddress.getAddressLine1()).append(" ");
        userAddressBuilder.append(userDefaultAddress.getAddressLine2()).append(" ");
        userAddressBuilder.append(userDefaultAddress.getDistrict()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getWard()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getCity()).append(", ");
        userAddressBuilder.append(userDefaultAddress.getCountry());

        // Get shipping method
        ShippingMethod shippingMethod = shippingMethodRepo.findById(orderRequest.getShippingMethodId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method not found!"));

        // Get payment method
        PaymentMethod paymentMethod = paymentMethodRepo.findById(orderRequest.getPaymentMethodId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found!"));

        // Merchandise Total
        AtomicReference<BigDecimal> merchandiseTotal = new AtomicReference<>(BigDecimal.ZERO);

        // Order
        ShopOrder order = new ShopOrder();

        // Order Items
        List<ShoppingCartItem> shoppingCartItems = cartItemRepo.findByShoppingCart_StatusAndShoppingCart_User(true, user);

        // Create order items
        List<OrderDetail> orderDetails = shoppingCartItems.stream()
                .map(item -> {
                    BigDecimal itemPrice = item.getProduct().getPrice();
                    BigDecimal itemSubTotal = new BigDecimal(item.getQuantity()).multiply(itemPrice);

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setProduct(item.getProduct());
                    orderDetail.setShopOrder(order);
                    orderDetail.setQuantity(item.getQuantity());
                    orderDetail.setPrice(itemPrice);

                    merchandiseTotal.updateAndGet(oldTotal -> oldTotal.add(itemSubTotal));

                    return orderDetail;

                })
                .collect(Collectors.toList());


        // create status
        OrderStatus orderStatus = orderStatusRepo.findByStatus(Order.PROCESSING)
                .orElseGet(() -> {
                    OrderStatus newStatus = OrderStatus.builder()
                            .status(Order.PROCESSING)
                            .build();

                    orderStatusRepo.save(newStatus);

                    return newStatus;
                });

        // create Transaction
        OrderTransaction orderTransaction = new OrderTransaction();

        // create Order and set value
        order.setUser(user);
        order.setOrderTransaction(orderTransaction);
        order.setUserComment(null);
        order.setUserFullName(userFullName);
        order.setUserPhone(userPhone);
        order.setShippingAddress(userAddressBuilder.toString());
        order.setShippingMethod(shippingMethod);
        order.setShippingFee(shippingMethod.getPrice());
        order.setMerchandiseTotal(merchandiseTotal.get());
        order.setTotal(shippingMethod.getPrice().add(merchandiseTotal.get()));
        order.setOrderStatus(orderStatus);
        order.setTrackingCode(UUID.randomUUID().toString());

        // set value for transaction, then saving
        orderTransaction.setPaymentMethod(paymentMethod);
        orderTransaction.setTransactionStatus(TransactionStatus.PENDING);
        orderTransaction.setTransactionAmount(shippingMethod.getPrice().add(merchandiseTotal.get()));
        orderTransactionRepo.save(orderTransaction);


        orderDetailRepo.saveAll(orderDetails);
        ShopOrder result = orderRepo.save(order);


        // if create order successfully, set Cart status is false
        if (result != null) {
            userCart.setStatus(false);
            cartRepo.save(userCart);
        }

    }

    @Override
    public ShopOrderDTO updateOrderStatus(Long orderId, Order status) {
        ShopOrder shopOrder = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!"));

        OrderStatus orderStatus = orderStatusRepo.findByStatus(status)
                .orElseGet(() -> {
                    OrderStatus newStatus = OrderStatus.builder()
                            .status(status)
                            .build();

                    orderStatusRepo.save(newStatus);

                    return newStatus;
                });

        if (status == Order.CANCELED) {
            // Check if payment method is COD
            OrderTransaction orderTransaction = shopOrder.getOrderTransaction();
            if (orderTransaction != null && orderTransaction.getPaymentMethod().getName().equals("COD")) {
                orderTransaction.setTransactionStatus(TransactionStatus.FAILED);
                orderTransactionRepo.save(orderTransaction);
            }
        }

        if (status == Order.DELIVERING) {
            // Check if payment method is COD
            OrderTransaction orderTransaction = shopOrder.getOrderTransaction();
            if (orderTransaction != null && orderTransaction.getPaymentMethod().getName().equals("COD")) {
                orderTransaction.setTransactionStatus(TransactionStatus.PENDING);
                orderTransactionRepo.save(orderTransaction);
            }
        }

        if (status == Order.COMPLETED) {
            // Check if payment method is COD
            OrderTransaction orderTransaction = shopOrder.getOrderTransaction();
            if (orderTransaction != null && orderTransaction.getPaymentMethod().getName().equals("COD")) {
                orderTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
                orderTransactionRepo.save(orderTransaction);
            }
        }
        shopOrder.setOrderStatus(orderStatus);
        orderRepo.save(shopOrder);

        ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
        shopOrderDTO.setId(shopOrder.getId());
        shopOrderDTO.setUserFullName(shopOrder.getUserFullName());
        shopOrderDTO.setUserComment(shopOrder.getUserComment());
        shopOrderDTO.setUserPhone(shopOrder.getUserPhone());
        shopOrderDTO.setShippingAddress(shopOrder.getShippingAddress());
        shopOrderDTO.setMerchandiseTotal(shopOrder.getMerchandiseTotal());
        shopOrderDTO.setShippingFee(shopOrder.getShippingFee());
        shopOrderDTO.setTotal(shopOrder.getTotal());
        shopOrderDTO.setPaymentMethod(shopOrder.getOrderTransaction().getPaymentMethod().getName());
        shopOrderDTO.setOrderStatus(shopOrder.getOrderStatus().getStatus());
        shopOrderDTO.setTransactionStatus(shopOrder.getOrderTransaction().getTransactionStatus());
        shopOrderDTO.setTrackingCode(shopOrder.getTrackingCode());

        List<ShopOrderDetailDTO> shopOrderDetailDTOs = shopOrder.getOrderDetails().stream()
                .map(itemDetail -> {
                    ShopOrderDetailDTO shopOrderDetailDTO = new ShopOrderDetailDTO();
                    shopOrderDetailDTO.setId(itemDetail.getId());
                    shopOrderDetailDTO.setProductName(itemDetail.getProduct().getName());
                    shopOrderDetailDTO.setPrice(itemDetail.getPrice());
                    shopOrderDetailDTO.setQuantity(itemDetail.getQuantity());
                    shopOrderDetailDTO.setMedia_url(itemDetail.getProduct().getMediaUrl());

                    return shopOrderDetailDTO;
                })
                .collect(Collectors.toList());

        shopOrderDTO.setOrderDetails(shopOrderDetailDTOs);

        return shopOrderDTO;
    }


    @Override
    public List<ShopOrderDTO> getAllOrders() {
        List<ShopOrder> shopOrders = orderRepo.findAll();

        List<ShopOrderDTO> shopOrderDTOS = shopOrders.stream()
                .map(item -> {
                    ShopOrderDTO temp = new ShopOrderDTO();

                    temp.setId(item.getId());
                    temp.setUserFullName(item.getUserFullName());
                    temp.setUserComment(item.getUserComment());
                    temp.setUserPhone(item.getUserPhone());
                    temp.setShippingAddress(item.getShippingAddress());
                    temp.setMerchandiseTotal(item.getMerchandiseTotal());
                    temp.setShippingFee(item.getShippingFee());
                    temp.setTotal(item.getTotal());
                    temp.setPaymentMethod(item.getOrderTransaction().getPaymentMethod().getName());
                    temp.setOrderStatus(item.getOrderStatus().getStatus());
                    temp.setTransactionStatus(item.getOrderTransaction().getTransactionStatus());
                    temp.setTrackingCode(item.getTrackingCode());

                    List<ShopOrderDetailDTO> shopOrderDetailDTOs = item.getOrderDetails().stream()
                            .map(itemDetail -> {
                                ShopOrderDetailDTO shopOrderDetailDTO = new ShopOrderDetailDTO();
                                shopOrderDetailDTO.setId(itemDetail.getId());
                                shopOrderDetailDTO.setProductName(itemDetail.getProduct().getName());
                                shopOrderDetailDTO.setPrice(itemDetail.getPrice());
                                shopOrderDetailDTO.setQuantity(itemDetail.getQuantity());
                                shopOrderDetailDTO.setMedia_url(itemDetail.getProduct().getMediaUrl());

                                return shopOrderDetailDTO;
                            })
                            .collect(Collectors.toList());

                    temp.setOrderDetails(shopOrderDetailDTOs);

                    return temp;

                })
                .collect(Collectors.toList());


        return shopOrderDTOS;
    }

    @Override
    public List<ShopOrderDTO> getOrdersByUserId(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        List<ShopOrder> shopOrders = orderRepo.findByUser(user);

        List<ShopOrderDTO> shopOrderDTOS = shopOrders.stream()
                .map(item -> {
                    ShopOrderDTO temp = new ShopOrderDTO();

                    temp.setId(item.getId());
                    temp.setUserFullName(item.getUserFullName());
                    temp.setUserComment(item.getUserComment());
                    temp.setUserPhone(item.getUserPhone());
                    temp.setShippingAddress(item.getShippingAddress());
                    temp.setMerchandiseTotal(item.getMerchandiseTotal());
                    temp.setShippingFee(item.getShippingFee());
                    temp.setTotal(item.getTotal());
                    temp.setPaymentMethod(item.getOrderTransaction().getPaymentMethod().getName());
                    temp.setOrderStatus(item.getOrderStatus().getStatus());
                    temp.setTransactionStatus(item.getOrderTransaction().getTransactionStatus());
                    temp.setTrackingCode(item.getTrackingCode());

                    List<ShopOrderDetailDTO> shopOrderDetailDTOs = item.getOrderDetails().stream()
                            .map(itemDetail -> {
                                ShopOrderDetailDTO shopOrderDetailDTO = new ShopOrderDetailDTO();
                                shopOrderDetailDTO.setId(itemDetail.getId());
                                shopOrderDetailDTO.setProductName(itemDetail.getProduct().getName());
                                shopOrderDetailDTO.setPrice(itemDetail.getPrice());
                                shopOrderDetailDTO.setQuantity(itemDetail.getQuantity());

                                return shopOrderDetailDTO;
                            })
                            .collect(Collectors.toList());

                    temp.setOrderDetails(shopOrderDetailDTOs);

                    return temp;

                })
                .collect(Collectors.toList());

        return shopOrderDTOS;
    }
}
