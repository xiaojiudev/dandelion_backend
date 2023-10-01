package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.UnitDTO;

import java.util.List;

public interface UnitService {

    UnitDTO createUnit(UnitDTO unitDTO);

    UnitDTO updateUnit(Long unitId, UnitDTO unitDTO);

    UnitDTO getUnitById(Long unitId);

    List<UnitDTO> getAllUnits();

    void deleteUnit(Long unitId);
}
