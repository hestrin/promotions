package com.ocado.basket.promotions.policies;

import com.ocado.basket.promotions.Basket;
import com.ocado.basket.promotions.Policy;

import java.math.BigDecimal;

public class MinimalBasketValue implements Policy {
    private final BigDecimal minimumValue;

    public MinimalBasketValue(BigDecimal minimumValue) {
        this.minimumValue = minimumValue;
    }

    @Override
    public boolean isApplicable(Basket basket) {
        return basket.getBasketValue().compareTo(minimumValue) >= 0;
    }
}
