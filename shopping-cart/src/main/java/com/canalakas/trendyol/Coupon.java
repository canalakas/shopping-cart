package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.DiscountType;

import java.math.BigDecimal;

public class Coupon {
    private BigDecimal minAmount;
    private float discount;
    private DiscountType discountType;

    public Coupon(BigDecimal minAmount, float discount, DiscountType discountType) {
        this.minAmount = minAmount;
        this.discount = discount;
        this.discountType = discountType;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
}
