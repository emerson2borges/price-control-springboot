package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.Supermarket;
import com.emerson.pricecontrol.exception.ResourceNotFoundException;
import com.emerson.pricecontrol.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupermarketService {

    private SupermarketRepository supermarketRepository;

    @Autowired
    public SupermarketService(SupermarketRepository supermarketRepository) {
        this.supermarketRepository = supermarketRepository;
    }

    public boolean existsByName(String name) {
        return supermarketRepository.existsByName(name);
    }

    public List<Supermarket> findAll() {
        return supermarketRepository.findAll();
    }

    public Optional<Supermarket> findById(Long id) {
        return supermarketRepository.findById(id);
    }

    public Supermarket createSupermarket(Supermarket supermarket) {
        if (existsByName(supermarket.getName())) {
            throw new IllegalArgumentException("Um supermercado com esté nome já existe");
        }
        return supermarketRepository.save(supermarket);
    }

    public Supermarket save(Supermarket supermarket) {
        return supermarketRepository.save(supermarket);
    }

    public Supermarket updateSupermarket(Long id, Supermarket supermarketDetails) {
        Supermarket supermarket = supermarketRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Supermercado não encontrado")
        );
        if (existsByName(supermarketDetails.getName())
            && !supermarketDetails.getName().equals(supermarket.getName())
        ) {
            throw new IllegalArgumentException("Um supermercado com esse nome já existe");
        }
        supermarket.setName(supermarketDetails.getName());
        supermarket.setDescription(supermarketDetails.getDescription());
        return supermarketRepository.save(supermarket);
    }

    public void deleteSupermarket(Long id) {
        Supermarket supermarket = supermarketRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Supermercado não encontrado")
        );
        supermarketRepository.delete(supermarket);
    }
}
