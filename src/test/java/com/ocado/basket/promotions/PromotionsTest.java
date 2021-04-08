package com.ocado.basket.promotions;

import org.junit.Test;

import static com.ocado.basket.promotions.TestConstants.*;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

public class PromotionsTest {

    private final DiscountCalculator discountCalculator = new DiscountCalculator(TestConstants.TEST_PROVIDER);

    @Test
    public void returnZeroDiscountForSmallBasket() {
        assertThat(discountCalculator.calculateDiscount(ONE_CHEAP_ITEM))
                .isEqualTo(ZERO);
    }

    @Test
    public void returnConstantDiscountForExpensiveBasket() {
        assertThat(discountCalculator.calculateDiscount(ONE_EXPENSIVE_ITEM))
                .isEqualTo(TEN);
    }

    @Test
    public void returnDiscountForBasketWithManyCheapItems() {
        assertThat(discountCalculator.calculateDiscount(MANY_CHEAP_ITEMS))
                .isEqualTo(FIVE);
    }

    @Test
    public void returnMaximumDiscountForBasketSatisfyingMultiplePromotions() {
        assertThat(discountCalculator.calculateDiscount(MANY_CHEAP_ITEMS_ARE_EXPENSIVE))
                .isEqualTo(TEN);
    }
}
