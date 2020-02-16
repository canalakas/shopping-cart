package com.canalakas.trendyol.discount;

import java.math.BigDecimal;

public class Discount implements IDiscount{

    DiscountType discountType;

    public Discount(DiscountType discountType) {
        this.discountType = discountType;
    }

    @Override
    public BigDecimal discount() {
        return null;
    }
}
