package com.emerson.pricecontrol.dto;

import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.entity.UnitMeasure;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Integer volume;
    private UnitMeasure unitMeasure;
    private Brand brand;

    public ProductDTO() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public UnitMeasure getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(UnitMeasure unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
