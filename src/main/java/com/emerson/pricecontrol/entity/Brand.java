package com.emerson.pricecontrol.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Table(name = "brand")
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O campo nome é obrigatório")
    private String name;

    @Column(length = 255, nullable = true)
    private String description;

    public Brand() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
