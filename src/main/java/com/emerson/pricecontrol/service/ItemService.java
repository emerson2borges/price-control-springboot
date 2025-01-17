package com.emerson.pricecontrol.service;

import com.emerson.pricecontrol.dto.ItemDTO;
import com.emerson.pricecontrol.entity.Item;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.entity.Supermarket;
import com.emerson.pricecontrol.repository.ItemRepository;
import com.emerson.pricecontrol.repository.ProductRepository;
import com.emerson.pricecontrol.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupermarketRepository supermarketRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(ItemDTO itemDTO) {
        Product product = productRepository.findById(itemDTO.getIdProduct())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        Supermarket supermarket = supermarketRepository.findById(itemDTO.getIdSupermarket())
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado"));

        Item item = toEntity(itemDTO, product, supermarket);

        return itemRepository.save(item);
    }

    public Item updateItem(ItemDTO itemDTO) {

        if (itemDTO.getId() == null) {
            throw new IllegalArgumentException("ID do item não fornecido.");
        }

        Item existingItem = itemRepository.findById(itemDTO.getId())
            .orElseThrow(() -> new RuntimeException("Item não encontrado com o ID fornecido"));

        Product product = productRepository.findById(itemDTO.getIdProduct())
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com o ID fornecido"));

        if (!existingItem.getProduct().getId().equals(itemDTO.getIdProduct())) {
            existingItem.setProduct(product);
        }

        Supermarket supermarket = supermarketRepository.findById(itemDTO.getIdSupermarket())
            .orElseThrow(() -> new RuntimeException("Supermercado não encontrado com o ID fornecido"));

        if (!existingItem.getSupermarket().getId().equals(itemDTO.getIdSupermarket())) {
            existingItem.setSupermarket(supermarket);
        }

        existingItem.setQuantity(itemDTO.getQuantity());
        existingItem.setPrice(itemDTO.getPrice());
        existingItem.setDiscount(itemDTO.getDiscount());
        existingItem.setDate(itemDTO.getDate());

        return itemRepository.save(existingItem);
    }

    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item não encontrado");
        }
        itemRepository.deleteById(id);
    }

    private Item toEntity(ItemDTO itemDTO, Product product, Supermarket supermarket) {
        return new Item(
            product,
            supermarket,
            itemDTO.getQuantity(),
            itemDTO.getPrice(),
            itemDTO.getDiscount(),
            itemDTO.getDate()
        );
    }
}
