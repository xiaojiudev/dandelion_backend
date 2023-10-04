package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.AddressDTO;

import java.util.List;

public interface AddressService {

    public AddressDTO createAddress(Long userId, AddressDTO addressDTO);

    public AddressDTO updateAddress(Long userId, AddressDTO addressDTO);

    public AddressDTO deleteAddress(Long userId, Long addressId);

    public List<AddressDTO> getAllAddress(Long userId);

    public AddressDTO getDefaultAddress(Long userId);
}
