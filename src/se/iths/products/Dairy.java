package se.iths.products;

import java.util.Objects;

public class Dairy extends Product {
    private final String category = "Dairy";


    public Dairy(double price, String brand, int ean, int stock) {
        super(price, brand, ean, stock);
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dairy dairy)) return false;
        return Objects.equals(category, dairy.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "Dairy{" +
                "category='" + category + '\'' +
                '}';
    }
}


