package com.tzuchaedahy.domain;

public class Product {
    private Integer id;
    private String name;
    private Double unitPrice;
    private Integer quantity;

    public Product(Integer id, String name, Double unit_price, Integer quantity) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome inválida!");
        }

        if (unit_price == null || unit_price <= 0) {
            throw new IllegalArgumentException("Preço inválido!");
        }

        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantidade inválida!");
        }

        this.id = id;
        this.name = name;
        this.unitPrice = unit_price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
