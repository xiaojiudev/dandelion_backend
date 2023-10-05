package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(Long userId, AddressDTO addressDTO);

    AddressDTO updateAddress(Long userId, Long addressId, AddressDTO addressDTO);

    void deleteAddress(Long userId, Long addressId);

    List<AddressDTO> getAllAddress(Long userId);

    AddressDTO getDefaultAddress(Long userId);

    void setDefaultAddress(Long userId, Long addressId);
}
