package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.User;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.UserPaymentInfoDTO;
import com.dandelion.backend.repositories.PaymentMethodRepo;
import com.dandelion.backend.repositories.UserRepo;
import com.dandelion.backend.services.UserPaymentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class UserPaymentServiceImpl implements UserPaymentService {

    private final PaymentMethodRepo paymentMethodRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;

    @Override
    public UserPaymentInfoDTO addPayment(Long userId, UserPaymentInfoDTO data) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        

        return null;
    }

    @Override
    public UserPaymentInfoDTO updatePayment(Long userId, Long paymentMethodId, UserPaymentInfoDTO data) {
        return null;
    }

    @Override
    public void deletePayment(Long userId, Long paymentMethodId) {

    }

    @Override
    public List<UserPaymentInfoDTO> getAllPayments(Long userId) {
        return null;
    }

    @Override
    public UserPaymentInfoDTO getPayment(Long userId, Long paymentMethodId) {
        return null;
    }
}
