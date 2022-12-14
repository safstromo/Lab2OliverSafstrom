package se.iths.products;

import java.util.Objects;

public class Dairy extends Product {

    public Dairy(String name, double price, int ean) {
        super(name, price, ean, Category.DAIRY);
    }

    public Category getCategory() {
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
        return "\nDairy{" +
                "category='" + category + '\'' +
                "name='" + super.getName()+ '\'' +
                ", price=" + super.getPrice() +
                ", ean=" + super.getEan() +
                ", stock=" + super.getStock() +
                "}";
    }
}


