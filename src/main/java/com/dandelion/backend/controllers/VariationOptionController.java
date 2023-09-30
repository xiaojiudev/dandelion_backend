package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.VariationOptionBody;
import com.dandelion.backend.payloads.VariationOptionDTO;
import com.dandelion.backend.services.VariationOptionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class VariationOptionController {

    @Autowired
    private VariationOptionService variationOptionService;

    @PostMapping("/options")
    public ResponseEntity<VariationOptionDTO> createOption(@RequestBody VariationOptionBody variationOptionBody) {
        return new ResponseEntity<>(variationOptionService.createOption(variationOptionBody), HttpStatus.CREATED);
    }

    @PutMapping("/options/{optionId}")
    public ResponseEntity<VariationOptionDTO> updateOption(@PathVariable("optionId") Long optionId, @RequestBody VariationOptionBody variationOptionBody) {
        return new ResponseEntity<>(variationOptionService.updateOption(optionId, variationOptionBody), HttpStatus.OK);
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<ApiResponse> deleteOption(@PathVariable("optionId") Long optionId) {
        variationOptionService.deleteOption(optionId);

        return new ResponseEntity<>(new ApiResponse("Option is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/options")
    public ResponseEntity<List<VariationOptionDTO>> getOptionsBy(
            @RequestParam(value = "variation", required = false) String variation,
            @RequestParam(value = "category", required = false) String category) {

        if (variation != null && category != null) {
            return new ResponseEntity<>(variationOptionService.getOptionsByVariationAndCategory(variation, category), HttpStatus.OK);
        }

        if (variation != null) {
            return new ResponseEntity<>(variationOptionService.getOptionsByVariation(variation), HttpStatus.OK);
        }

        if (category != null) {
            return new ResponseEntity<>(variationOptionService.getOptionsByCategory(category), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/options")
//    public ResponseEntity<List<VariationOptionDTO>> getOptionByCategory(@RequestParam("category") String category) {
//        return new ResponseEntity<>(variationOptionService.getOptionsByCategory(category), HttpStatus.OK);
//    }
}
