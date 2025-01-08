package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
