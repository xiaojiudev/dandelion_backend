package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.PaymentMethod;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.PaymentMethodDTO;
import com.dandelion.backend.repositories.PaymentMethodRepo;
import com.dandelion.backend.services.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepo;
    private final ModelMapper modelMapper;

    @Override
    public PaymentMethodDTO createPaymentMethod(PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod paymentMethod = modelMapper.map(paymentMethodDTO, PaymentMethod.class);

        paymentMethodRepo.save(paymentMethod);

        return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }

    @Override
    public PaymentMethodDTO updatePaymentMethod(Long paymentId, PaymentMethodDTO paymentMethodDTO) {
        PaymentMethod existPaymentMethod = paymentMethodRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found!"));

        existPaymentMethod.setName(paymentMethodDTO.getName());
        existPaymentMethod.setIsEnabled(paymentMethodDTO.getIsEnabled());

        paymentMethodRepo.save(existPaymentMethod);

        return modelMapper.map(existPaymentMethod, PaymentMethodDTO.class);
    }

    @Override
    public void deletePaymentMethod(Long paymentId) {
        PaymentMethod existPaymentMethod = paymentMethodRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found!"));

        paymentMethodRepo.delete(existPaymentMethod);
    }

    @Override
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        List<PaymentMethod> paymentMethodList = paymentMethodRepo.findAll();

        List<PaymentMethodDTO> paymentMethodDTOList = paymentMethodList.stream()
                .map(item -> modelMapper.map(item, PaymentMethodDTO.class))
                .collect(Collectors.toList());

        return paymentMethodDTOList;
    }

    @Override
    public PaymentMethodDTO getPaymentMethod(Long paymentId) {
        PaymentMethod paymentMethod = paymentMethodRepo.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found!"));
        return modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }
}
