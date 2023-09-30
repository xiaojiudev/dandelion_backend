package com.dandelion.backend.services;

import com.dandelion.backend.entities.Category;
import com.dandelion.backend.entities.Variation;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.VariationBody;
import com.dandelion.backend.payloads.VariationDTO;
import com.dandelion.backend.repositories.CategoryRepo;
import com.dandelion.backend.repositories.VariationRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VariationServiceImpl implements VariationService {

    @Autowired
    private VariationRepo variationRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public VariationDTO createVariation(VariationBody variationBody) {

        Category tempCate = categoryRepo.findById(variationBody.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + variationBody.getCategoryId()));

        Variation variation = modelMapper.map(variationBody, Variation.class);
        variation.setCategory(tempCate);

        variationRepo.save(variation);

        return modelMapper.map(variation, VariationDTO.class);
    }

    @Override
    public VariationDTO updateVariation(Long variationId, VariationBody variationBody) {

        Variation tempVar = variationRepo.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException("Variation not found with id = " + variationId));

        Category tempCate = categoryRepo.findById(variationBody.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id = " + variationBody.getCategoryId()));

        tempVar.setName(variationBody.getName());
        tempVar.setCategory(tempCate);

        variationRepo.save(tempVar);

        return modelMapper.map(tempVar, VariationDTO.class);
    }

    @Override
    public VariationDTO getVariationById(Long variationId) {

        Variation variation = variationRepo.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException("Variation not found with id = " + variationId));


        return modelMapper.map(variation, VariationDTO.class);
    }

    @Override
    public List<VariationDTO> getVariationsByCategory(String category) {

        List<Variation> variations = variationRepo.findByCategory_NameIgnoreCase(category);

        if (variations.isEmpty()) {
            throw new ResourceNotFoundException("Variation not found for this category = " + category);
        }

        List<VariationDTO> variationDTOs = variations.stream()
                .map(item -> modelMapper.map(item, VariationDTO.class))
                .collect(Collectors.toList());

        return variationDTOs;
    }

    @Override
    public void deleteVariation(Long variationId) {

        Variation variation = variationRepo.findById(variationId)
                .orElseThrow(() -> new ResourceNotFoundException("Variation not found with id = " + variationId));

        variationRepo.delete(variation);

    }

    //    @Override
//    public List<VariationDTO> getAllVariations() {
//
//        List<Variation> variations = variationRepo.findAll();
//
//        List<VariationDTO> variationDTOs = variations.stream()
//                .map(item -> modelMapper.map(item, VariationDTO.class))
//                .collect(Collectors.toList());
//
//        return variationDTOs;
//    }
}
