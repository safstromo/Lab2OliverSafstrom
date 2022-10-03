package se.iths.products;

import java.util.Objects;

public class Products extends Product {
    Category category = Category.FRUIT;

    public Products(String name, double price, int ean) {
        super(name, price, ean);

    }

    public Category getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Products fruit)) return false;
        return Objects.equals(category, fruit.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "\nFruit{" +
                "category='" + category + '\'' +
                "name='" + super.getName()+ '\'' +
                ", price=" + super.getPrice() +
                ", ean=" + super.getEan() +
                ", stock=" + super.getStock() +
                "}";
    }
}


