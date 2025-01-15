package com.emerson.pricecontrol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemDTO {

    private Long id;
    private Long idProduct;
    private Long idSupermarket;
    private Integer quantity;
    private BigDecimal price;
    private Boolean discount;
    private LocalDate date;

    public ItemDTO() {}

    public ItemDTO(
        Long id,
        Long id_product,
        Long id_supermarket,
        Integer quantity,
        BigDecimal price,
        Boolean discount,
        LocalDate date
    ) {
        this.id = id;
        this.idProduct = id_product;
        this.idSupermarket = id_supermarket;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.date = date;
    }

    public ItemDTO(
        Long id_product,
        Long id_supermarket,
        Integer quantity,
        BigDecimal price,
        Boolean discount,
        LocalDate date
    ) {
        this.idProduct = id_product;
        this.idSupermarket = id_supermarket;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long id_product) {
        this.idProduct = id_product;
    }

    public Long getIdSupermarket() {
        return idSupermarket;
    }

    public void setIdSupermarket(Long id_supermarket) {
        this.idSupermarket = id_supermarket;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getDiscount() {
        return discount;
    }

    public void setDiscount(Boolean discount) {
        this.discount = discount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", product=" + idProduct +
            ", supermarket=" + idSupermarket +
            ", quantity=" + quantity +
            ", price=" + price +
            ", discount=" + discount +
            ", date=" + date +
        "}";
    }
}
