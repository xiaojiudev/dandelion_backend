package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.AddressDTO;
import com.dandelion.backend.services.AddressService;
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

    @PostMapping("/user/{userId}/address")
    public ResponseEntity<AddressDTO> createAddress(
            @PathVariable("userId") Long userId,
            @RequestBody AddressDTO addressRequest) {
        return new ResponseEntity<>(addressService.createAddress(userId, addressRequest), HttpStatus.CREATED);
    }

    @PostMapping("/user/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse> setDefaultAddress(
            @PathVariable("userId") Long userId,
            @PathVariable("addressId") Long addressId) {

        addressService.setDefaultAddress(userId, addressId);

        return new ResponseEntity<>(new ApiResponse("Set default address successfully", true), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable("userId") Long userId,
            @PathVariable("addressId") Long addressId,
            @RequestBody AddressDTO addressRequest) {
        return new ResponseEntity<>(addressService.updateAddress(userId, addressId, addressRequest), HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/address/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(
            @PathVariable("userId") Long userId,
            @PathVariable("addressId") Long addressId) {

        addressService.deleteAddress(userId, addressId);

        return new ResponseEntity<>(new ApiResponse("Address is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/address")
    public ResponseEntity<List<AddressDTO>> getAllAddress(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(addressService.getAllAddress(userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/address/default")
    public ResponseEntity<AddressDTO> getDefaultAddress(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(addressService.getDefaultAddress(userId), HttpStatus.OK);
    }


}
