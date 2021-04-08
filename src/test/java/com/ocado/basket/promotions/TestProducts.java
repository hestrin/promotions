package com.ocado.basket.promotions;

public class TestProducts {
    static final Product milk = Product.builder().name("milk").price(1).build();
    static final Product expensiveItem = Product.builder().name("exp_item").price(150).build();
    static final Product cheapItem = Product.builder().name("cheap_item").price(0.5).build();
    static final Product sok = Product.builder().name("sok").price(1.5).build();

}
