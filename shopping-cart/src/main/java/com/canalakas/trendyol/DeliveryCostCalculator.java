package com.canalakas.trendyol;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.ToIntFunction;

public class DeliveryCostCalculator {

    private BigDecimal costPerDelivery;
    private BigDecimal costPerProduct;
    private BigDecimal fixedCost;

    public DeliveryCostCalculator(BigDecimal costPerDelivery, BigDecimal costPerProduct, BigDecimal fixedCost) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public BigDecimal getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(BigDecimal fixedCost) {
        this.fixedCost = fixedCost;
    }

    public BigDecimal getCostPerDelivery() {
        return costPerDelivery;
    }

    public void setCostPerDelivery(BigDecimal costPerDelivery) {
        this.costPerDelivery = costPerDelivery;
    }

    public BigDecimal getCostPerProduct() {
        return costPerProduct;
    }

    public void setCostPerProduct(BigDecimal costPerProduct) {
        this.costPerProduct = costPerProduct;
    }

    public BigDecimal calculateFor(ShoppingCart cart) {
        int sum = cart.getProducts().values().stream().mapToInt(new ToIntFunction<HashMap<Product, Integer>>() {
            @Override
            public int applyAsInt(HashMap<Product, Integer> value) {
                return value.size();
            }
        }).sum();
        return costPerDelivery.multiply(BigDecimal.valueOf(cart.getProducts().size()))
                .add(costPerProduct.multiply(BigDecimal.valueOf(sum)))
                .add(fixedCost);
    }
}
