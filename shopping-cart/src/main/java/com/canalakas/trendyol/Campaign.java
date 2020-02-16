package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.DiscountType;

public class Campaign {
    private Category category;
    private float discount;
    private int quantity;
    private DiscountType discountType;

    public Campaign(Category category, float discount, int quantity, DiscountType discountType) {
        this.category = category;
        this.discount = discount;
        this.quantity = quantity;
        this.discountType = discountType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
