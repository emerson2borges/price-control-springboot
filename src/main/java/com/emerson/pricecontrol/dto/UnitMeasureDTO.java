package com.emerson.pricecontrol.dto;

import com.emerson.pricecontrol.entity.UnitMeasure;

public class UnitMeasureDTO {

    private Long id;
    private String name;
    private String description;

    public UnitMeasureDTO() {}

    public UnitMeasureDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static UnitMeasureDTO fromUnitMeasure(UnitMeasure unitMeasure) {
        return new UnitMeasureDTO(unitMeasure.getId(), unitMeasure.getName(), unitMeasure.getDescription());
    }

    public static UnitMeasure toUnitMeasure(UnitMeasureDTO unitMeasureDTO) {
        UnitMeasure unitMeasure = new UnitMeasure();
        unitMeasure.setId(unitMeasureDTO.getId());
        unitMeasure.setName(unitMeasureDTO.getName());
        unitMeasure.setDescription(unitMeasureDTO.getDescription());
        return unitMeasure;
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

    public void setName(String unit) {
        this.name = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
