package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.repository.BrandRepository;
import com.emerson.pricecontrol.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    private BrandRepository brandRepository ;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository ;
    }

    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand createBrand(Brand brand) {
        if (existsByName(brand.getName())) {
            throw new IllegalArgumentException("Marca com este nome já existe.");
        }
        return save(brand);
    }

    public Brand save(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand brandDetails) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Marca não encontrada")
        );
        if (existsByName(brandDetails.getName())
            && !brand.getName().equals(brandDetails.getName())
        ) {
            throw new IllegalArgumentException("Marca com esse nome já existe.");
        }
        brand.setName(brandDetails.getName());
        brand.setDescription(brandDetails.getDescription()); return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Marca não encontrada")
        );
        brandRepository.delete(brand);
    }
}
