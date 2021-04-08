package com.ocado.basket.promotions;

import java.util.ArrayList;
import java.util.List;

public interface PromotionProvider {
    default List<Promotion> getPromotions() { return new ArrayList<>(); }
}
