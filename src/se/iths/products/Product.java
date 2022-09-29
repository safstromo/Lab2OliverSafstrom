package se.iths.products;

import se.iths.FoodStore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Product implements Serializable{
    private final String name;
    private BigDecimal price;
    private final int ean;
    private int stock;

    public Product(String name, double price, int ean) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.ean = ean;
    }

    public Product(String name) {
        this.name = name;
        this.ean = 0;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = BigDecimal.valueOf(price);
    }

    public int getEan() {
        return ean;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return ean == product.ean && stock == product.stock && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, ean, stock);
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", ean=" + ean +
                ", stock=" + stock +
                "}";
    }

    public enum Category{
        FRUIT,
        DAIRY,
        MEATS,
        FISH,
        FROZEN,
        DRINKS,
        DRYFOODS,

    }
}

