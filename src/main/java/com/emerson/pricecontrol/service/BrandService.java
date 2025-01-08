package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository ;

    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }
}
