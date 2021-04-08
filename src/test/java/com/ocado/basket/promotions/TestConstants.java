package com.ocado.basket.promotions;

import java.math.BigDecimal;
import java.util.HashMap;

import static com.ocado.basket.promotions.TestProducts.*;

public class TestConstants {
    static final BigDecimal TEN = BigDecimal.valueOf(10);
    static final BigDecimal FIVE = BigDecimal.valueOf(5);

    static Basket ONE_CHEAP_ITEM = new SingleItemBasket(milk, 1);
    static Basket ONE_EXPENSIVE_ITEM = new SingleItemBasket(expensiveItem, 1);
    static Basket MANY_CHEAP_ITEMS = new SingleItemBasket(cheapItem, 50);
    static Basket MANY_CHEAP_ITEMS_ARE_EXPENSIVE = new SingleItemBasket(sok, 100);

    static final PromotionProvider TEST_PROVIDER = TestPromotionProvider.getInstance();

    private static class SingleItemBasket extends Basket {
        public SingleItemBasket(Product product, Integer quantity) {
            super(new HashMap<>() {{
                put(product, quantity);
            }});
        }
    }
}
