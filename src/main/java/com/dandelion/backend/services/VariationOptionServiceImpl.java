package com.dandelion.backend.services;


import com.dandelion.backend.entities.Variation;
import com.dandelion.backend.entities.VariationOption;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.VariationOptionBody;
import com.dandelion.backend.payloads.VariationOptionDTO;
import com.dandelion.backend.repositories.VariationOptionRepo;
import com.dandelion.backend.repositories.VariationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VariationOptionServiceImpl implements VariationOptionService {

    @Autowired
    private VariationOptionRepo variationOptionRepo;

    @Autowired
    private VariationRepo variationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public VariationOptionDTO createOption(VariationOptionBody variationOptionBody) {

        Variation tempVar = variationRepo.findById(variationOptionBody.getVariationId())
                .orElseThrow(() -> new ResourceNotFoundException("Variation not found with id = " + variationOptionBody.getVariationId()));

        VariationOption tempVarOpt = modelMapper.map(variationOptionBody, VariationOption.class);
        tempVarOpt.setVariation(tempVar);

        variationOptionRepo.save(tempVarOpt);

        return modelMapper.map(tempVarOpt, VariationOptionDTO.class);
    }

    @Override
    public VariationOptionDTO updateOption(Long optionId, VariationOptionBody variationOptionBody) {

        VariationOption tempVarOpt = variationOptionRepo.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id = " + optionId));

        Variation tempVar = variationRepo.findById(variationOptionBody.getVariationId())
                .orElseThrow(() -> new ResourceNotFoundException("Variation not found with id = " + variationOptionBody.getVariationId()));


        tempVarOpt.setValue(variationOptionBody.getValue());
        tempVarOpt.setVariation(tempVar);

        variationOptionRepo.save(tempVarOpt);


        return modelMapper.map(tempVarOpt, VariationOptionDTO.class);
    }

    @Override
    public void deleteOption(Long optionId) {

        VariationOption tempVarOpt = variationOptionRepo.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id = " + optionId));

        variationOptionRepo.delete(tempVarOpt);
    }

    @Override
    public List<VariationOptionDTO> getOptionsByVariation(String variation) {

        List<VariationOption> variationOptions = variationOptionRepo.findByVariation_NameIgnoreCase(variation);

        if (variationOptions.isEmpty()) {
            throw new ResourceNotFoundException("Options not found with variation = " + variation);
        }

        List<VariationOptionDTO> variationOptionDTOs = variationOptions.stream()
                .map((element) -> modelMapper.map(element, VariationOptionDTO.class))
                .collect(Collectors.toList());


        return variationOptionDTOs;
    }

    @Override
    public List<VariationOptionDTO> getOptionsByCategory(String category) {
        List<VariationOption> variationOptions = variationOptionRepo.findByVariation_Category_NameIgnoreCase(category);

        if (variationOptions.isEmpty()) {
            throw new ResourceNotFoundException("Options not found with category = " + category);
        }

        List<VariationOptionDTO> variationOptionDTOs = variationOptions.stream()
                .map((element) -> modelMapper.map(element, VariationOptionDTO.class))
                .collect(Collectors.toList());

        return variationOptionDTOs;
    }

    @Override
    public List<VariationOptionDTO> getOptionsByVariationAndCategory(String variation, String category) {

        List<VariationOption> variationOptions = variationOptionRepo.findByVariation_NameIgnoreCaseAndVariation_Category_NameIgnoreCase(variation, category);

        if (variationOptions.isEmpty()) {
            throw new ResourceNotFoundException("Option not found!");
        }

        List<VariationOptionDTO> variationOptionDTOs = variationOptions.stream()
                .map((element) -> modelMapper.map(element, VariationOptionDTO.class))
                .collect(Collectors.toList());

        return variationOptionDTOs;
    }

    @Override
    public VariationOptionDTO getOptionById(Long optionId) {
        return null;
    }
}
