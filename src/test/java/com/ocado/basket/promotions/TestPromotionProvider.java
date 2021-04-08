package com.ocado.basket.promotions;

import com.ocado.basket.promotions.policies.MinimalBasketValue;
import com.ocado.basket.promotions.policies.MinimumNumberOfItems;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.util.List;

import static com.ocado.basket.promotions.TestConstants.TEN;
import static com.ocado.basket.promotions.TestConstants.FIVE;

public class TestPromotionProvider implements PromotionProvider {
    Policy ENOUGH_VALUE = new MinimalBasketValue(BigDecimal.valueOf(100));
    Policy ENOUGH_ITEMS = new MinimumNumberOfItems(40);

    List<Promotion> promotions = Lists.newArrayList(new Promotion(FIVE, ENOUGH_ITEMS), new Promotion(TEN, ENOUGH_VALUE));

    private TestPromotionProvider() {}

    private static final PromotionProvider instance = new TestPromotionProvider();

    static PromotionProvider getInstance() {
        return instance;
    }

    @Override
    public List<Promotion> getPromotions() {
        return promotions;
    }
}
