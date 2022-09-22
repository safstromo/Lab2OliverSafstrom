package se.iths.products;

import java.util.Objects;

public class Fruit extends Product {
    final String category = "Fruit";

    public Fruit(String name, double price, int ean) {
        super(name, price, ean);
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Fruit fruit)) return false;
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


