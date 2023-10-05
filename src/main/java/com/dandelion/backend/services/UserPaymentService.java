package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.UserPaymentInfoDTO;

import java.util.List;

public interface UserPaymentService {

    UserPaymentInfoDTO addPayment(Long userId, UserPaymentInfoDTO data);

    UserPaymentInfoDTO updatePayment(Long userId, Long paymentMethodId, UserPaymentInfoDTO data);

    void deletePayment(Long userId, Long paymentMethodId);

    List<UserPaymentInfoDTO> getAllPayments(Long userId);

    UserPaymentInfoDTO getPayment(Long userId, Long paymentMethodId);

}
