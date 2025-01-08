package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupermarketService {

    @Autowired
    private SupermarketRepository supermarketRepository;

    public boolean existsByName(String name) {
        return supermarketRepository.existsByName(name);
    }
}
