package se.iths.products;

import java.util.Objects;

public class Fruit extends Product{
    final String category = "Fruit";


    Fruit(double price, String brand, int ean, int stock) {
        super(price, brand, ean, stock);
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
        return "Fruit{" +
                "category='" + category + '\'' +
                '}';
    }
}


