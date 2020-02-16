package com.canalakas.trendyol;

import com.canalakas.trendyol.discount.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Shopping Cart Class
 *
 * Shopping Cart application is a e-commerce application that you can buy any product with any quantity of any category.
 * While shopping discount calculations operated for customers, If the discount terms fit, proper discount (whether campaign or coupon)
 * will be calculated on total price.
 *
 * To do so I choose to use decorator pattern. Because eventually campaign discount calculated on products and coupon discount
 * calculated on the cart. It is not important calculated discount would be zero or more. Eventually discounts will be run
 * on the price. Therefore I rather implement decorator pattern than strategy. I create discount object. when I need the
 * discount object, I evolve it (make the object have feature) to calculate campaign or coupon discount in run time.
 *
 * Case study does not say anything about parent category so it stands unused property
 *
 * When I run campaign discount calculation, I accept greater than (not greater than uquals) #quantity
 * in which is set during new campaign object creation
 */
public class ShoppingCart {
    private HashMap<Category, HashMap<Product, Integer>> products = new HashMap<>();
    private DeliveryCostCalculator deliveryCostCalculator;
    private IDiscount discountDecorator;

    private BigDecimal totalPrice = BigDecimal.ZERO;
    private BigDecimal campaignDiscount = BigDecimal.ZERO;
    private BigDecimal couponDiscount = BigDecimal.ZERO;

    public HashMap<Category, HashMap<Product, Integer>> getProducts() {
        return products;
    }

    /**
     * function take product and its quantity, add it to the cart
     * also calculate the total price on which is not applied discounts
     * @param product
     * @param quantity
     */
    public void addItem(Product product, int quantity) {

        if (!products.containsKey(product.getCategory())) {
            products.put(product.getCategory(), new HashMap<>());
        }

        HashMap<Product, Integer> productMap = products.get(product.getCategory());
        Integer value = productMap.putIfAbsent(product, quantity);
        if (value != null) {
            productMap.put(product, value + quantity);
        }
        totalPrice = totalPrice.add(product.getPrice().multiply(new BigDecimal(String.valueOf(quantity))));

    }

    /**
     * apply campaign discount on total price
     * @param campaigns
     */
    public void applyCampaignDiscount(Campaign... campaigns) {
        totalPrice = totalPrice.subtract(getCampaignDiscount(campaigns));
    }

    /**
     * apply coupon discount on total price
     * @param coupon
     */
    public void applyCoupon(Coupon coupon) {
        totalPrice = totalPrice.subtract(getCouponDiscount(coupon));
    }

    /**
     * apply campaign and coupon discount on total price in order
     * then get the discounted total price
     * @param coupon
     * @param campaigns
     * @return totalPrice
     */
    public BigDecimal getTotalAmountAfterDiscount(Coupon coupon, Campaign... campaigns) {
        applyCampaignDiscount(campaigns);
        applyCoupon(coupon);

        return totalPrice;
    }

    /**
     * calculate the proper campaign discount among the all campaign
     * using decorator design pattern
     * @param campaigns
     * @return campaignDiscount
     */
    private BigDecimal getCampaignDiscount(Campaign... campaigns) {
        for (Campaign campaign : campaigns) {
            if (!products.containsKey(campaign.getCategory()))
                continue;

            if (products.get(campaign.getCategory()).values().stream().reduce(0, Integer::sum) > campaign.getQuantity()) {
                HashMap<Product, Integer> productMap = products.get(campaign.getCategory());
                discountDecorator = new CampaignDiscountDecorator(new Discount(campaign.getDiscountType()),campaign,productMap);
                campaignDiscount = campaignDiscount.max(discountDecorator.discount());
            }
        }
        return campaignDiscount;
    }

    /**
     * calculate coupon discount on cart using decorator design pattern
     * @param coupon
     * @return couponDiscount
     */
    private BigDecimal getCouponDiscount(Coupon coupon) {
        discountDecorator = new CouponDiscountDecorator(new Discount(coupon.getDiscountType()),coupon,totalPrice);
        couponDiscount = discountDecorator.discount();
        return couponDiscount;
    }

    /**
     * calculate and return delivery cost of the cart
     * @return deliveryCost
     */
    public BigDecimal getDeliveryCost() {
        return deliveryCostCalculator.calculateFor(this);
    }

    /**
     * set delivery cost calculaator object to cart
     * @param deliveryCostCalculator
     */
    public void setDeliveryCostCalculator(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
    }

    /**
     * get total price
     * @return totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * print method
     */
    public void print() {
        for (Map.Entry<Category, HashMap<Product, Integer>> entry : products.entrySet()) {
            System.out.println("Category Name --> " + entry.getKey().getTitle());
            entry.getValue()
                    .forEach((k, v) -> System.out.println("Product Name -> " + k.getTitle() +
                            " Quantity -> " + v + " Unit Price -> " + k.getPrice() + " Total Price -> " + totalPrice +
                            " Total Discount -> " + campaignDiscount.add(couponDiscount)));
        }
        BigDecimal deliveryCost = getDeliveryCost();
        System.out.println("TOTAL AMOUNT --> " + totalPrice + " TOTAL AMOUNT included delivery cost--> " + totalPrice.add(deliveryCost) + " Delivery Cost --> " + deliveryCost);
    }
}
