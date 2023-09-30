package com.dandelion.backend.services;

import com.dandelion.backend.payloads.VariationBody;
import com.dandelion.backend.payloads.VariationDTO;

import java.util.List;

public interface VariationService {

    VariationDTO createVariation(VariationBody variationBody);

    VariationDTO updateVariation(Long variationId, VariationBody variationBody);

    VariationDTO getVariationById(Long variationId);

    List<VariationDTO> getVariationsByCategory(String category);

    void deleteVariation(Long variationId);

    //    List<VariationDTO> getAllVariations();
}
