package se.iths.products;

public abstract class Product {
    private double price;
    private final String brand;
    private final int ean;
    private int stock;

    public double getPrice() {
        return price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public int getStock() {
        return stock;
    }

    public int getEan() {
        return ean;
    }

    Product(double price, String brand, int ean, int stock) {
        this.price = price;
        this.brand = brand;
        this.ean = ean;
        this.stock = stock;
    }
}

