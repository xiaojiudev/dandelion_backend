package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.UnitDTO;
import com.dandelion.backend.services.UnitService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PostMapping("/units")
    public ResponseEntity<UnitDTO> createCategory(@Valid @RequestBody UnitDTO unitDTO) {
        return new ResponseEntity<>(unitService.createUnit(unitDTO), HttpStatus.CREATED);
    }

    @PutMapping("/units/{unitId}")
    public ResponseEntity<UnitDTO> updateCategory(@PathVariable("unitId") Long id, @Valid @RequestBody UnitDTO unitDTO) {
        return new ResponseEntity<>(unitService.updateUnit(id, unitDTO), HttpStatus.OK);
    }

    @DeleteMapping("/units/{unitId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("unitId") Long id) {

        unitService.deleteUnit(id);

        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/units")
    public ResponseEntity<List<UnitDTO>> getAllCategories() {
        return new ResponseEntity<>(unitService.getAllUnits(), HttpStatus.OK);
    }

    @GetMapping("/units/{unitId}")
    public ResponseEntity<UnitDTO> getCategory(@PathVariable("unitId") Long id) {
        return new ResponseEntity<>(unitService.getUnitById(id), HttpStatus.OK);
    }
}
