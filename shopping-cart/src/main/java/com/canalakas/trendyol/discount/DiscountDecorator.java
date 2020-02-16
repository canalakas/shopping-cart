package com.canalakas.trendyol.discount;

public abstract class DiscountDecorator implements IDiscount {
    protected IDiscount discount;

    public DiscountDecorator(IDiscount discount) {
        this.discount = discount;
    }
}
