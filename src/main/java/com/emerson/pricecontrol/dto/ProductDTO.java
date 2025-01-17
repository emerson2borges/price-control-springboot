package com.emerson.pricecontrol.dto;

import com.emerson.pricecontrol.entity.Brand;
import com.emerson.pricecontrol.entity.Product;
import com.emerson.pricecontrol.entity.UnitMeasure;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private UnitMeasure unitMeasure;
    private Brand brand;

    public ProductDTO() { }

    public ProductDTO(Long id, String name, String description, UnitMeasure unitMeasure, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unitMeasure = unitMeasure;
        this.brand = brand;
    }

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getUnitMeasure(), product.getBrand());
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setUnitMeasure(productDTO.getUnitMeasure());
        product.setBrand(productDTO.getBrand());
        return product;
    }

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
