package com.emerson.pricecontrol.dto;

import com.emerson.pricecontrol.entity.Supermarket;

public class SupermarketDTO {

    private Long id;
    private String name;
    private String description;

    public SupermarketDTO() {}

    public SupermarketDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static SupermarketDTO fromSupermarket(Supermarket supermarket) {
        return new SupermarketDTO(supermarket.getId(), supermarket.getName(), supermarket.getDescription());
    }

    public static Supermarket toSupermarket(SupermarketDTO supermarketDTO) {
        Supermarket supermarket = new Supermarket();
        supermarket.setId(supermarketDTO.getId());
        supermarket.setName(supermarketDTO.getName());
        supermarket.setDescription(supermarketDTO.getDescription());
        return supermarket;
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
