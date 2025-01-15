package com.emerson.pricecontrol.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_supermarket", nullable = false)
    private Supermarket supermarket;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "discount", nullable = true)
    private Boolean discount;

    @Column(nullable = false)
    private LocalDate date;

    // Construtores
    public Item() {
    }

    public Item(Product idProduct, Supermarket idSupermarket, Integer quantity, BigDecimal price, Boolean discount, LocalDate date) {
        this.product = idProduct;
        this.supermarket = idSupermarket;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supermarket getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(Supermarket supermarket) {
        this.supermarket = supermarket;
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
            ", product=" + product +
            ", supermarket=" + supermarket +
            ", quantity=" + quantity +
            ", price=" + price +
            ", discount=" + discount +
            ", date=" + date +
        "}";
    }
}
