package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.Unit;
import com.dandelion.backend.exceptions.ResourceAlreadyExistsException;
import com.dandelion.backend.payloads.dto.UnitDTO;
import com.dandelion.backend.repositories.UnitRepo;
import com.dandelion.backend.services.UnitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    @Autowired
    private UnitRepo unitRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UnitDTO createUnit(UnitDTO unitDTO) {

        Unit unit = modelMapper.map(unitDTO, Unit.class);

        Optional<Unit> tempUnit = unitRepo.findByNameIgnoreCase(unit.getName());

        if (tempUnit.isPresent()) {
            throw new ResourceAlreadyExistsException("Unit already exist with id = " + tempUnit.get().getId());
        }

        Unit addedUnit = unitRepo.save(unit);

        return modelMapper.map(addedUnit, UnitDTO.class);
    }

    @Override
    public UnitDTO updateUnit(Long unitId, UnitDTO unitDTO) {
        return null;
    }

    @Override
    public UnitDTO getUnitById(Long unitId) {
        return null;
    }

    @Override
    public List<UnitDTO> getAllUnits() {
        return null;
    }

    @Override
    public void deleteUnit(Long unitId) {

    }
}
