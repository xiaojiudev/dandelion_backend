package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Address;
import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.AddressDTO;
import com.dandelion.backend.repositories.AddressRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.AddressService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(Long userId, AddressDTO addressDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        boolean isDefault = !addressRepo.existsByIsDefaultAndUser_Id(true, userId);

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(user);
        address.setIsDefault(isDefault);

        Address addedAddress = addressRepo.save(address);


        return modelMapper.map(addedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long userId, Long addressId, AddressDTO addressRequest) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Address address = addressRepo.findByIdAndUser_Id(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!"));

        address.setAddressLine1(addressRequest.getAddressLine1());
        address.setAddressLine2(addressRequest.getAddressLine2());
        address.setPhone(addressRequest.getPhone());
        address.setCity(addressRequest.getCity());
        address.setDistrict(addressRequest.getDistrict());
        address.setWard(addressRequest.getWard());
        address.setCountry(addressRequest.getCountry());
        address.setPostalCode(addressRequest.getPostalCode());

        addressRepo.save(address);


        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Address address = addressRepo.findByIdAndUser_Id(addressId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        addressRepo.delete(address);
    }

    @Override
    public List<AddressDTO> getAllAddress(Long userId) {

        List<Address> addressList = addressRepo.findByUser_Id(userId);

        return addressList.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getDefaultAddress(Long userId) {
        Address userAddress = addressRepo.findByIsDefaultAndUser_Id(true, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User or address not found!"));

        return modelMapper.map(userAddress, AddressDTO.class);
    }

    @Override
    public void setDefaultAddress(Long userId, Long addressId) {
        Address address = addressRepo.findByIdAndUser_Id(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("User or address not found!"));

        Optional<Address> existingAddressDf = addressRepo.findByIsDefaultAndUser_Id(true, userId);
        if (existingAddressDf.isPresent()) {
            existingAddressDf.get().setIsDefault(false);
        }

        address.setIsDefault(true);
        addressRepo.save(address);
    }
}
