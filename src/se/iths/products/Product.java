package se.iths.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Product implements Serializable{
    private final String name;
    private final BigDecimal price;
    private final int ean;
    private int stock;
    final Category category;

    public Product(String name, double price, int ean, Category category) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.ean = ean;
        this.category = category;
    }

    public String getName() {
            return name;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
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
        DAIRY,
        DRINKS,
        DRYFOODS,
        FISH,
        FROZEN,
        FRUIT,
        MEATS,

    }
}

