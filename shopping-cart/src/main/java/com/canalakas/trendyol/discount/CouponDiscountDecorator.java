package com.canalakas.trendyol.discount;

import com.canalakas.trendyol.Coupon;

import java.math.BigDecimal;

public class CouponDiscountDecorator extends DiscountDecorator {

    Coupon coupon;
    BigDecimal totalPrice;
    public CouponDiscountDecorator(IDiscount discount, Coupon coupon, BigDecimal totalPrice) {
        super(discount);
        this.coupon = coupon;
        this.totalPrice = totalPrice;
    }

    @Override
    public BigDecimal discount() {
        if (totalPrice.compareTo(coupon.getMinAmount()) > 0) {
            if (coupon.getDiscountType() == DiscountType.Rate) {
                return totalPrice.multiply(new BigDecimal(Float.toString(coupon.getDiscount() / 100.0f)));
            } else {
                return new BigDecimal(Float.toString(coupon.getDiscount()));
            }
        }
        return BigDecimal.ZERO;
    }
}
