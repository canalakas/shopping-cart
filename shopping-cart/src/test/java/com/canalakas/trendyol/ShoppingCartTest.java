package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class ShoppingCartTest {

    @Test
    public void shoppingCartTest() {
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        Product banana = new Product("Banana", new BigDecimal("12.0"), food);
        cart.addItem(banana, 2);

        Category electronics = new Category("Electronics");
        Product phone = new Product("Phone", new BigDecimal("250.0"), electronics);
        Product computer = new Product("Computer", new BigDecimal("320.0"), electronics);
        Product printer = new Product("Printer", new BigDecimal("75.0"), electronics);

        cart.addItem(phone, 1);
        cart.addItem(computer, 1);
        Campaign campaign2 = new Campaign(electronics, 17.0f, 1, DiscountType.Amount);

        cart.addItem(printer, 4);
        Campaign campaign3 = new Campaign(electronics, 20.0f, 5, DiscountType.Rate);

        Coupon coupon = new Coupon(new BigDecimal("700.0"), 122.44f, DiscountType.Amount);
        cart.applyCampaignDiscount(campaign1, campaign2, campaign3);
        cart.applyCoupon(coupon);

        DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(
                new BigDecimal("10.0"), new BigDecimal("3.0"), new BigDecimal("2.99"));
        cart.setDeliveryCostCalculator(deliveryCostCalculator);

        assertEquals(new BigDecimal("655.55"), cart.getTotalPrice().add(cart.getDeliveryCost()).setScale(2, RoundingMode.CEILING));

        cart.print();
    }
}
