package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class CouponDiscountTest {

    @Test
    public void test1(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Coupon coupon = new Coupon(new BigDecimal("19.0"), 2.0f, DiscountType.Rate);

        cart.applyCoupon(coupon);

        //%2 discount applied. Because total price is greater than coupon min amount.
        assertEquals(new BigDecimal("19.60"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void test2(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("15.0"), food);
        Product orange = new Product("Orange", new BigDecimal("14.0"), food);

        cart.addItem(apple, 2);
        cart.addItem(orange, 5);
        Coupon coupon = new Coupon(new BigDecimal("50.0"), 5.0f, DiscountType.Rate);

        cart.applyCoupon(coupon);

        //%5 discount applied. Because total price is greater than coupon min amount.
        assertEquals(new BigDecimal("95.00"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void test3(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        Product banana = new Product("Banana", new BigDecimal("12.0"), food);
        cart.addItem(banana,2);

        Category electronics = new Category("Electronics");
        Product phone = new Product("Phone", new BigDecimal("250.0"), electronics);

        cart.addItem(phone, 1);
        Campaign campaign2 = new Campaign(electronics, 30.0f, 3, DiscountType.Amount);

        Coupon coupon = new Coupon(new BigDecimal("250.0"),34.12f, DiscountType.Amount);

        cart.applyCampaignDiscount(campaign1,campaign2);
        cart.applyCoupon(coupon);

        //8.80 discount for campaign1, there is no discount for campaign2. Because quantity is not fit.
        //After campaign discounts applied still total price is applicable to run coupon discount. So 34.12 is discounted from price
        assertEquals(new BigDecimal("251.08"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void test4(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        Product banana = new Product("Banana", new BigDecimal("12.0"), food);
        cart.addItem(banana,2);

        Category electronics = new Category("Electronics");
        Product phone = new Product("Phone", new BigDecimal("250.0"), electronics);
        Product computer = new Product("Computer", new BigDecimal("320.0"), electronics);

        cart.addItem(phone, 1);
        cart.addItem(computer, 1);
        Campaign campaign2 = new Campaign(electronics, 17.0f, 1, DiscountType.Amount);

        Coupon coupon = new Coupon(new BigDecimal("500.0"),7.5f, DiscountType.Rate);
        cart.applyCampaignDiscount(campaign1,campaign2);
        cart.applyCoupon(coupon);

        //8.80 discount for campaign1, 17.0 discount for campaign2. campaign2 discount is max. So campaign2 will be applied.
        //After campaign discounts applied still total price is applicable to run coupon discount. So %7.5 is discounted from price
        assertEquals(new BigDecimal("552.23"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void test5(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        Product banana = new Product("Banana", new BigDecimal("12.0"), food);
        cart.addItem(banana,2);

        Category electronics = new Category("Electronics");
        Product phone = new Product("Phone", new BigDecimal("250.0"), electronics);
        Product computer = new Product("Computer", new BigDecimal("320.0"), electronics);
        Product printer = new Product("Printer", new BigDecimal("75.0"), electronics);

        cart.addItem(phone, 1);
        cart.addItem(computer, 1);
        Campaign campaign2 = new Campaign(electronics, 17.0f, 1, DiscountType.Amount);

        cart.addItem(printer,4);
        Campaign campaign3 = new Campaign(electronics, 20.0f, 5, DiscountType.Rate);

        Coupon coupon = new Coupon(new BigDecimal("700.0"),122.44f, DiscountType.Amount);
        cart.applyCampaignDiscount(campaign1,campaign2,campaign3);
        cart.applyCoupon(coupon);

        //8.80 discount for campaign1, 17.0 discount for campaign2. 174.0 discount for campaign3.
        // campaign3 discount is max. So campaign3 will be applied.
        //After campaign discounts applied still total price is applicable to run coupon discount. So 122.44 is discounted from price
        assertEquals(new BigDecimal("617.56"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }
}
