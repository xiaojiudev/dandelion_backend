package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.UserPaymentInfoDTO;

import java.util.List;

public interface PaymentMethodService {

    UserPaymentInfoDTO addPayment(Long userId, UserPaymentInfoDTO data);

    UserPaymentInfoDTO updatePayment(Long userId, Long paymentMethodId, UserPaymentInfoDTO data);

    void deletePayment(Long userId, Long paymentMethodId);

    List<UserPaymentInfoDTO> getAllPayments();

}
