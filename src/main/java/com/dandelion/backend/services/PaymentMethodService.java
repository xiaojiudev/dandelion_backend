package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.PaymentMethodDTO;

import java.util.List;

public interface PaymentMethodService {

    PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO);

    PaymentMethodDTO updatePaymentMethod(Long paymentId, PaymentMethodDTO paymentMethodDTO);

    void deletePaymentMethod(Long paymentId);

    List<PaymentMethodDTO> getAllPaymentMethods();

    PaymentMethodDTO getPaymentMethod(Long paymentId);
}
