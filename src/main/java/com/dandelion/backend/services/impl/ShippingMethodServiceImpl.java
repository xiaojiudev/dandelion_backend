package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.ShippingMethod;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.ShippingMethodDTO;
import com.dandelion.backend.repositories.ShippingMethodRepo;
import com.dandelion.backend.services.ShippingMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ShippingMethodServiceImpl implements ShippingMethodService {

    private final ShippingMethodRepo shippingMethodRepo;
    private final ModelMapper modelMapper;

    @Override
    public ShippingMethodDTO createShippingMethod(ShippingMethodDTO shippingMethodDTO) {

        ShippingMethod shippingMethod = modelMapper.map(shippingMethodDTO, ShippingMethod.class);

        shippingMethodRepo.save(shippingMethod);

        return modelMapper.map(shippingMethod, ShippingMethodDTO.class);
    }

    @Override
    public ShippingMethodDTO updateShippingMethod(Long methodId, ShippingMethodDTO shippingMethodDTO) {

        ShippingMethod existShippingMethod = shippingMethodRepo.findById(methodId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method not found!"));

        existShippingMethod.setName(shippingMethodDTO.getName());
        existShippingMethod.setPrice(shippingMethodDTO.getPrice());

        shippingMethodRepo.save(existShippingMethod);

        return modelMapper.map(existShippingMethod, ShippingMethodDTO.class);
    }

    @Override
    public void deleteShippingMethod(Long methodId) {
        ShippingMethod existShippingMethod = shippingMethodRepo.findById(methodId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method not found!"));

        shippingMethodRepo.delete(existShippingMethod);
    }

    @Override
    public List<ShippingMethodDTO> getAllShippingMethods() {
        List<ShippingMethod> shippingMethodList = shippingMethodRepo.findAll();

        List<ShippingMethodDTO> shippingMethodDTOList = shippingMethodList.stream()
                .map(item -> modelMapper.map(item, ShippingMethodDTO.class))
                .collect(Collectors.toList());

        return shippingMethodDTOList;
    }

    @Override
    public ShippingMethodDTO getShippingMethod(Long methodId) {
        ShippingMethod shippingMethod = shippingMethodRepo.findById(methodId)
                .orElseThrow(() -> new ResourceNotFoundException("Shipping method not found!"));
        return modelMapper.map(shippingMethod, ShippingMethodDTO.class);
    }
}
