package com.dandelion.backend.services;

import com.dandelion.backend.payloads.VariationOptionBody;
import com.dandelion.backend.payloads.VariationOptionDTO;

import java.util.List;

public interface VariationOptionService {

    VariationOptionDTO createOption(VariationOptionBody variationOptionBody);

    VariationOptionDTO updateOption(Long optionId, VariationOptionBody variationOptionBody);

    void deleteOption(Long optionId);

    List<VariationOptionDTO> getOptionsByVariation(String variation);

    List<VariationOptionDTO> getOptionsByCategory(String category);

    List<VariationOptionDTO> getOptionsByVariationAndCategory(String variation, String category);

    VariationOptionDTO getOptionById(Long optionId);
}
