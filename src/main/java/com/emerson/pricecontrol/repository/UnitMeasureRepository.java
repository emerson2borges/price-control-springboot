package com.emerson.pricecontrol.repository;

import com.emerson.pricecontrol.entity.UnitMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, Long> {
}
