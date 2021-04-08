package com.ocado.basket.promotions;

import java.math.BigDecimal;

public class Promotion {
    private final BigDecimal discount;
    private final Policy policy;

    public Promotion(BigDecimal discount, Policy policy) {
        this.discount = discount;
        this.policy = policy;
    }

    public boolean isApplicable(Basket basket) {
        return policy.isApplicable(basket);
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
