package com.ocado.basket.promotions;

public interface Policy {
    boolean isApplicable(Basket basket);
}
