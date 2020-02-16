package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.DiscountType;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

public class CampaignDiscountTest {

    @Test
    public void test1(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        cart.applyCampaignDiscount(campaign1);

        //I expect not discount applied. Because quantity is less than required
        assertEquals(new BigDecimal("20.00"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void test2(){
        ShoppingCart cart = new ShoppingCart();

        Category food = new Category("food");
        Product apple = new Product("Apple", new BigDecimal("10.0"), food);

        cart.addItem(apple, 2);
        Campaign campaign1 = new Campaign(food, 20.0f, 3, DiscountType.Rate);

        Product banana = new Product("Banana", new BigDecimal("12.0"), food);
        cart.addItem(banana,2);

        cart.applyCampaignDiscount(campaign1);

        //I add two more product whit same category so that I can apply campaign discount on these products
        assertEquals(new BigDecimal("35.20"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
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

        cart.applyCampaignDiscount(campaign1,campaign2);

        //8.80 discount for campaign1, there is no discount for campaign2. Because quantity is not fit.
        assertEquals(new BigDecimal("285.20"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
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

        cart.applyCampaignDiscount(campaign1,campaign2);

        //8.80 discount for campaign1, 17.0 discount for campaign2. campaign2 discount is max. So campaign2 will be applied.
        assertEquals(new BigDecimal("597.00"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
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

        cart.applyCampaignDiscount(campaign1,campaign2,campaign3);

        //8.80 discount for campaign1, 17.0 discount for campaign2. 174.0 discount for campaign3.
        // campaign3 discount is max. So campaign3 will be applied.
        assertEquals(new BigDecimal("740.00"), cart.getTotalPrice().setScale(2, RoundingMode.CEILING));
    }
}
