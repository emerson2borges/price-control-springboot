package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.dto.UnitMeasureDTO;
import com.emerson.pricecontrol.entity.UnitMeasure;
import com.emerson.pricecontrol.repository.UnitMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitMeasureService {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    public boolean existsByName(String name) {
        return unitMeasureRepository.existsByName(name);
    }
}
