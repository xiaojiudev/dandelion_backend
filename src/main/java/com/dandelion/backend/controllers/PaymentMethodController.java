package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.PaymentMethodDTO;
import com.dandelion.backend.services.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @PostMapping("/payment-methods")
    public ResponseEntity<PaymentMethodDTO> createMethod(@Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        return new ResponseEntity<>(paymentMethodService.createPaymentMethod(paymentMethodDTO), HttpStatus.CREATED);
    }

    @PutMapping("/payment-methods/{methodId}")
    public ResponseEntity<PaymentMethodDTO> updateMethod(
            @PathVariable("methodId") Long paymentId,
            @Valid @RequestBody PaymentMethodDTO paymentMethodDTO) {
        return new ResponseEntity<>(paymentMethodService.updatePaymentMethod(paymentId, paymentMethodDTO), HttpStatus.OK);
    }

    @DeleteMapping("/payment-methods/{methodId}")
    public ResponseEntity<ApiResponse> deleteMethod(@PathVariable("methodId") Long methodId) {

        paymentMethodService.deletePaymentMethod(methodId);

        return new ResponseEntity<>(new ApiResponse("Payment method is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/payment-methods")
    public ResponseEntity<List<PaymentMethodDTO>> getAllShippingMethods() {

        return new ResponseEntity<>(paymentMethodService.getAllPaymentMethods(), HttpStatus.OK);
    }

    @GetMapping("/payment-methods/{methodId}")
    public ResponseEntity<PaymentMethodDTO> getShippingMethod(@PathVariable("methodId") Long methodId) {

        return new ResponseEntity<>(paymentMethodService.getPaymentMethod(methodId), HttpStatus.OK);
    }
}
