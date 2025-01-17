package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.entity.Item;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.entity.Supermarket;
import com.emerson.pricecontrol.repository.ItemRepository;
import com.emerson.pricecontrol.repository.ProductRepository;
import com.emerson.pricecontrol.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private ItemRepository itemRepository;
    private ProductRepository productRepository;

    @Autowired
    public ReportService(ItemRepository itemRepository, ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public BigDecimal findLowestPriceForProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Produto não encontrado para o ID: " + productId);
        }
        List<Item> items = itemRepository.findByProductOrderByPriceAsc(product);
        if (items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.getFirst().getPrice();
    }

    public LocalDate findCheapestDayForProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Produto não encontrado para o ID: " + productId);
        }
        List<Item> items = itemRepository.findByProductOrderByPriceAsc(product);
        if (items.isEmpty()) {
            return null;
        }
        return items.getFirst().getDate();
    }

}
