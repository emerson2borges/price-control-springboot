package com.emerson.pricecontrol.repository;

import com.emerson.pricecontrol.entity.Item;
import com.emerson.pricecontrol.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Metodo para encontrar produtos ordenado por preco ascendente
    List<Item> findByProductOrderByPriceAsc(Product product);

}
