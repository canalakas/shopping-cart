package com.canalakas.trendyol;

import java.math.BigDecimal;

public class Product {

    private String title;
    private BigDecimal price;
    private Category category;

    public Product() {
    }

    public Product(String title, BigDecimal price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
