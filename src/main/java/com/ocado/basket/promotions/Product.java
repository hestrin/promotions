package com.ocado.basket.promotions;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final String name;
    private final BigDecimal price;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public BigDecimal getPrice() {
        return price;
    }

    private Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    static class ProductBuilder{
        private String name;
        private BigDecimal price;

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = BigDecimal.valueOf(price);
            return this;
        }

        public Product build() {
            return new Product(name, price);
        }
    }
}
