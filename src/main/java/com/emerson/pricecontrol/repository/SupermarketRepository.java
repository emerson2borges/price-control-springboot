package com.emerson.pricecontrol.repository;

import com.emerson.pricecontrol.entity.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    boolean existsByName(String name);
}
