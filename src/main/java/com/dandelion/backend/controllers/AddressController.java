package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.dto.AddressDTO;
import com.dandelion.backend.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address/{userId}")
    public ResponseEntity<AddressDTO> createAddress(
            @PathVariable("userId") Long userId,
            @RequestBody AddressDTO addressDTO) {
        return new ResponseEntity<>(addressService.createAddress(userId, addressDTO), HttpStatus.CREATED);
    }
}
