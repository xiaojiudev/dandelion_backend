package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.AddressDTO;
import com.dandelion.backend.services.AddressService;
import com.dandelion.backend.utils.CurrentUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressRequest) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(addressService.createAddress(userId, addressRequest), HttpStatus.CREATED);
    }

    @PostMapping("/address/{addressId}")
    public ResponseEntity<ApiResponse> setDefaultAddress(@PathVariable("addressId") Long addressId) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        addressService.setDefaultAddress(userId, addressId);

        return new ResponseEntity<>(new ApiResponse("Set default address successfully", true), HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressRequest) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(addressService.updateAddress(userId, addressId, addressRequest), HttpStatus.OK);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(
            @PathVariable("addressId") Long addressId) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        addressService.deleteAddress(userId, addressId);

        return new ResponseEntity<>(new ApiResponse("Address is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressDTO>> getAllAddress() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(addressService.getAllAddress(userId), HttpStatus.OK);
    }

    @GetMapping("/address/default")
    public ResponseEntity<AddressDTO> getDefaultAddress() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(addressService.getDefaultAddress(userId), HttpStatus.OK);
    }


}
