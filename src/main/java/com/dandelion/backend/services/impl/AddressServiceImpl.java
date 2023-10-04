package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Address;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.AddressDTO;
import com.dandelion.backend.repositories.AddressRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(Long userId, AddressDTO addressDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Optional<Address> temp = addressRepo.findByAddressLine1AndUser_Id(addressDTO.getAddressLine1(), user.getId());
        if (temp.isPresent()) {
            throw new ResourceAlreadyExistsException("Address is already exist!");
        }

        Optional<Address> userAddress = addressRepo.findByIsDefaultAndUser_Id(true, user.getId());

        if (userAddress.isEmpty()) {
            addressDTO.setIsDefault(true);
        } else {
            addressDTO.setIsDefault(false);
        }

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);

        Address addedAddress = addressRepo.save(address);


        return modelMapper.map(addedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long userId, AddressDTO addressDTO) {
        return null;
    }

    @Override
    public AddressDTO deleteAddress(Long userId, Long addressId) {
        return null;
    }

    @Override
    public List<AddressDTO> getAllAddress(Long userId) {
        return null;
    }

    @Override
    public AddressDTO getDefaultAddress(Long userId) {
        return null;
    }
}
