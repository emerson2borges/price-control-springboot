package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.exception.ResourceNotFoundException;
import com.emerson.pricecontrol.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        if (existsByName(product.getName())) {
            throw new IllegalArgumentException("Um produto com este nome já existe");
        }
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Produto não encontrado")
        );
        if (existsByName(productDetails.getName())
            && !productDetails.getName().equals(product.getName())
        ) {
            throw new IllegalArgumentException("Um produto com esse nome já existe");
        }
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Produto não encontrado")
        );
        productRepository.delete(product);
    }


}
