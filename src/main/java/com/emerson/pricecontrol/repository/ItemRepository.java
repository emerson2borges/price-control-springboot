package com.emerson.pricecontrol.repository;

import com.emerson.pricecontrol.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Metodo para encontrar itens por supermercado (exemplo de metodo personalizado)
    List<Item> findBySupermarket_Id(Long supermarketId);

    // Metodo para encontrar itens por produto
    List<Item> findByProduct_Id(Long productId);
}
