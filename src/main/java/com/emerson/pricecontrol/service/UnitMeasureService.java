package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.UnitMeasure;
import com.emerson.pricecontrol.exception.ResourceNotFoundException;
import com.emerson.pricecontrol.repository.UnitMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitMeasureService {
    private UnitMeasureRepository unitMeasureRepository;

    @Autowired
    public UnitMeasureService(UnitMeasureRepository unitMeasureRepository) {
        this.unitMeasureRepository = unitMeasureRepository;
    }

    public List<UnitMeasure> findAll() {
        return unitMeasureRepository.findAll();
    }

    public Optional<UnitMeasure> findById(Long id) {
        return unitMeasureRepository.findById(id);
    }

    public UnitMeasure createUnitMeasure(UnitMeasure unitMeasure) {
        if (existsByName(unitMeasure.getName())) {
            throw new IllegalArgumentException("Uma unidade de medida com esse nome já existe");
        }
        return save(unitMeasure);
    }

    public UnitMeasure save(UnitMeasure unitMeasure) { return unitMeasureRepository.save(unitMeasure); }

    public UnitMeasure updateUnitMeasure(Long id, UnitMeasure unitMeasureDetails) {
        UnitMeasure unitMeasure = unitMeasureRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Unidade de medida não encontrada")
        );
        if (existsByName(unitMeasureDetails.getName())
            && !unitMeasure.getName().equals(unitMeasureDetails.getName())
        ) {
            throw new IllegalArgumentException("Unidade de medida com esse nome já existe");
        }
        unitMeasure.setName(unitMeasureDetails.getName());
        unitMeasure.setDescription(unitMeasureDetails.getDescription());
        return unitMeasureRepository.save(unitMeasure);
    }

    public void deleteUnitMeasure(Long id) {
        UnitMeasure unitMeasure = findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Unidade de medida não encontrada")
        );
        unitMeasureRepository.delete(unitMeasure);
    }

    public boolean existsByName(String name) {
        return unitMeasureRepository.existsByName(name);
    }
}
