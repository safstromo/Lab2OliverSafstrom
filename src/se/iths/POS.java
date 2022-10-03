package se.iths;

import com.google.gson.Gson;
import se.iths.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static se.iths.InventoryManagement.*;

public interface POS {


    static void startPOS(Scanner sc, Gson gson) {
        do {
            printMenuPos();
            menuSwitchPos(sc, FoodStoreMain.products, FoodStoreMain.cart, gson);
        } while (true);
    }

    static void menuSwitchPos(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart, Gson gson) {
        String menuChoice = getMenuChoice(sc);

        switch (menuChoice) {
            case "1" -> addProductToCart(sc, products, cart);
            case "2" -> removeProductToCart(sc, cart);
            case "3" -> showCart(cart);
            case "4" -> checkout(cart, gson); // TODO LOOKS OF THE PRINT
            case "E" -> {
                System.out.println("Good bye!");
                System.exit(0);
            }
            default -> InventoryManagement.printError();
        }
    }

    private static void checkout(ArrayList<Product> cart, Gson gson) {
        showCart(cart);
        writeToJSON(cart, gson);
        cart.forEach(product -> product.setStock(product.getStock() - 1));
        cart.clear();
    }

    private static void removeProductToCart(Scanner sc, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);

        try {
            List<Product> productToBeRemoved = getProductsByEan(cart, productToFind);
            System.out.println(productToFind.toUpperCase() + " removed from cart");
            cart.removeAll(productToBeRemoved);
        } catch (NumberFormatException e) {
            List<Product> productToBeRemoved = getProductByName(cart, productToFind);
            System.out.println(productToFind.toUpperCase() + " removed from cart");
            cart.removeAll(productToBeRemoved);
        }
    } // TODO BUG REMOVES ALL PRODUCTS IN CATEGORY?

    private static void addProductToCart(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);
        try {
            List<Product> findProductByEan = getProductsByEan(products, productToFind);
            findProductByEan.forEach(product -> {
                checkIfInStock(cart, productToFind, findProductByEan, product);
            });
        } catch (NumberFormatException e) {
            List<Product> findProductByName = getProductByName(products, productToFind);
            findProductByName.forEach(product -> {
                checkIfInStock(cart, productToFind, findProductByName, product);
            });
        }
    }

    private static void checkIfInStock(ArrayList<Product> cart, String productToFind, List<Product> findProductByEan, Product product) {
        if (product.getStock() > 0) {
            System.out.println(productToFind.toUpperCase() + " Added to cart");
            cart.addAll(findProductByEan);
        } else {
            System.out.println("Product not in stock.");
        }
    }

    private static List<Product> getProductsByEan(ArrayList<Product> products, String productToFind) {
        return products.stream().filter(p -> p.getEan() == Integer.parseInt(productToFind)).toList();
    }

    private static List<Product> getProductByName(ArrayList<Product> products, String productToFind) {
        return products.stream().filter(p -> p.getName().equals(productToFind)).toList();
    }

    static void showCart(ArrayList<Product> cart) {
        BigDecimal sum = cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Items in cart:");
        cart.forEach(System.out::println);
        System.out.println("Total price is : $" + sum);
    }

    private static void printMenuPos() {
        System.out.println("""
                            
                   Food store
                  Point of sale
                      Menu
                __________________
                1.Add product to cart
                2.Remove product from cart
                3.Show cart
                4.Checkout
                e.Exit
                ------------------""");
    }
}
