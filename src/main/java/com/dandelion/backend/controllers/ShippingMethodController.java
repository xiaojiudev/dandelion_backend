package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.ShippingMethodDTO;
import com.dandelion.backend.services.ShippingMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShippingMethodController {

    private final ShippingMethodService shippingMethodService;

    @PostMapping("/shipping-methods")
    public ResponseEntity<ShippingMethodDTO> createMethod(@Valid @RequestBody ShippingMethodDTO shippingMethodDTO) {
        return new ResponseEntity<>(shippingMethodService.createShippingMethod(shippingMethodDTO), HttpStatus.CREATED);
    }

    @PutMapping("/shipping-methods/{methodId}")
    public ResponseEntity<ShippingMethodDTO> updateMethod(
            @PathVariable("methodId") Long methodId,
            @Valid @RequestBody ShippingMethodDTO shippingMethodDTO) {
        return new ResponseEntity<>(shippingMethodService.updateShippingMethod(methodId, shippingMethodDTO), HttpStatus.OK);
    }

    @DeleteMapping("/shipping-methods/{methodId}")
    public ResponseEntity<ApiResponse> deleteMethod(@PathVariable("methodId") Long methodId) {

        shippingMethodService.deleteShippingMethod(methodId);

        return new ResponseEntity<>(new ApiResponse("Shipping method is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/shipping-methods")
    public ResponseEntity<List<ShippingMethodDTO>> getAllShippingMethods() {

        return new ResponseEntity<>(shippingMethodService.getAllShippingMethods(), HttpStatus.OK);
    }

    @GetMapping("/shipping-methods/{methodId}")
    public ResponseEntity<ShippingMethodDTO> getShippingMethod(@PathVariable("methodId") Long methodId) {

        return new ResponseEntity<>(shippingMethodService.getShippingMethod(methodId), HttpStatus.OK);
    }
}
