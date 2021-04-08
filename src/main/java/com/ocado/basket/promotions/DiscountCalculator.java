package com.ocado.basket.promotions;

import com.ocado.basket.utils.Utils;

import java.math.BigDecimal;

public class DiscountCalculator {
    private final PromotionProvider promotionProvider;

    public DiscountCalculator(PromotionProvider promotionProvider) {
        this.promotionProvider = promotionProvider;
    }

    public BigDecimal calculateDiscount(Basket basket) {
        return promotionProvider.getPromotions().stream()
                .filter(p -> p.isApplicable(basket))
                .map(Promotion::getDiscount)
                .reduce(Utils::max).orElse(BigDecimal.ZERO);
    }
}
