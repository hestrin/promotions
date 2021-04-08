package com.ocado.basket.promotions.policies;

import com.ocado.basket.promotions.Basket;
import com.ocado.basket.promotions.Policy;

public class MinimumNumberOfItems implements Policy {
    private final int minimalNumberOfItems;

    public MinimumNumberOfItems(int minimalNumberOfItems) {
        this.minimalNumberOfItems = minimalNumberOfItems;
    }

    @Override
    public boolean isApplicable(Basket basket) {
        return basket.getItemsCount() > minimalNumberOfItems;
    }

}
