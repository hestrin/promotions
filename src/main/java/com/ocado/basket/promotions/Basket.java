package com.ocado.basket.promotions;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

public class Basket {

    private final Map<Product, Integer> products;

    public Basket(Map<Product, Integer> products) {
        this.products = products;
    }

    public int getItemsCount() {
        return products.values().stream().mapToInt(Integer::intValue).sum();
    }

    public BigDecimal getBasketValue() {
        return products.entrySet().stream()
                .map(e -> e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())))
                .reduce(BigDecimal::add).orElse(ZERO);
    }
}
