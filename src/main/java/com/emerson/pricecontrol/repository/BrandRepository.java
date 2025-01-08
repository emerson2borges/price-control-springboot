package com.emerson.pricecontrol.repository;

import com.emerson.pricecontrol.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository  extends JpaRepository<Brand, Long> {
    boolean existsByName(String name);
}
