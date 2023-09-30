package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.VariationBody;
import com.dandelion.backend.payloads.VariationDTO;
import com.dandelion.backend.services.VariationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class VariationController {

    @Autowired
    private VariationService variationService;

    @PostMapping("/variations")
    public ResponseEntity<VariationDTO> createVariation(@RequestBody VariationBody variationBody) {
        return new ResponseEntity<>(variationService.createVariation(variationBody), HttpStatus.CREATED);
    }

    @PutMapping("/variations/{variationId}")
    public ResponseEntity<VariationDTO> updateVariation(@PathVariable("variationId") Long variationId, @RequestBody VariationBody variationBody) {
        return new ResponseEntity<>(variationService.updateVariation(variationId, variationBody), HttpStatus.OK);
    }

    @DeleteMapping("/variations/{variationId}")
    public ResponseEntity<ApiResponse> deleteVariation(@PathVariable("variationId") Long variationId) {
        variationService.deleteVariation(variationId);

        return new ResponseEntity<>(new ApiResponse("Variation is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/variations/{variationId}")
    public ResponseEntity<VariationDTO> getVariationById(@PathVariable("variationId") Long variationId) {
        return new ResponseEntity<>(variationService.getVariationById(variationId), HttpStatus.OK);
    }

    @GetMapping("/variations")
    public ResponseEntity<List<VariationDTO>> getVariationByCategory(@RequestParam("category") String category) {
        return new ResponseEntity<List<VariationDTO>>(variationService.getVariationsByCategory(category), HttpStatus.OK);
    }


//    @GetMapping("/variations")
//    public ResponseEntity<List<VariationDTO>> getAllVariations() {
//        return new ResponseEntity<>(variationService.getAllVariations(), HttpStatus.OK);
//    }
}
