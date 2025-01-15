package com.emerson.pricecontrol.dto;

import com.emerson.pricecontrol.entity.Brand;

public class BrandDTO {

    private Long id;
    private String name;
    private String description;

    public BrandDTO() {}

    public BrandDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static BrandDTO fromEntity(Brand brand) {
        return new BrandDTO(brand.getId(), brand.getName(), brand.getDescription());
    }

    public static Brand toEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        brand.setId(brandDTO.getId());
        brand.setName(brandDTO.getName());
        brand.setDescription(brandDTO.getDescription());
        return brand;
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
}
