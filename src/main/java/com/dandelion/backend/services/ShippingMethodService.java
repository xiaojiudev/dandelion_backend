package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.ShippingMethodDTO;

import java.util.List;

public interface ShippingMethodService {

    ShippingMethodDTO createShippingMethod(ShippingMethodDTO shippingMethodDTO);

    ShippingMethodDTO updateShippingMethod(Long methodId, ShippingMethodDTO shippingMethodDTO);

    void deleteShippingMethod(Long methodId);

    List<ShippingMethodDTO> getAllShippingMethods();

    ShippingMethodDTO getShippingMethod(Long methodId);
}
