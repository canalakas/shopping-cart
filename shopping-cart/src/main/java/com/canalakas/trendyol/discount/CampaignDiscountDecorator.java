package com.canalakas.trendyol.discount;

import com.canalakas.trendyol.Campaign;
import com.canalakas.trendyol.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CampaignDiscountDecorator extends DiscountDecorator {

    Campaign campaign;
    HashMap<Product, Integer> products;
    public CampaignDiscountDecorator(IDiscount discount, Campaign campaign, HashMap<Product, Integer> products) {
        super(discount);
        this.campaign = campaign;
        this.products = products;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public BigDecimal discount() {
        if (campaign.getDiscountType() == DiscountType.Rate) {
            BigDecimal categoryTotalPrice = BigDecimal.ZERO;
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Integer quantity = entry.getValue();
                categoryTotalPrice = categoryTotalPrice.add(product.getPrice().multiply(new BigDecimal(String.valueOf(quantity))));
            }
            return categoryTotalPrice.multiply(new BigDecimal(Float.toString(campaign.getDiscount() / 100.0f)));
        } else {
            return new BigDecimal(Float.toString(campaign.getDiscount()));
        }

    }
}
