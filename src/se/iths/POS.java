package se.iths;

import com.google.gson.Gson;
import se.iths.products.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static se.iths.InventoryManagement.*;

public class POS {


    static void startPOS(Scanner sc, Gson gson) {
        do {
            printMenuPos();
            menuSwitchPos(sc, FoodStoreMain.products, FoodStoreMain.cart, gson);
        } while (true);
    }

    static void menuSwitchPos(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart, Gson gson) {
        String menuChoice = getMenuChoice(sc);

        switch (menuChoice) {
            case "1" -> printProductsSortedByName(products);
            case "2" -> addProductToCart(sc, products, cart);
            case "3" -> removeProductToCart(sc, cart);
            case "4" -> showCart(cart, getCartSum(cart));
            case "5" -> checkout(sc, cart, gson);
            case "E" -> {
                System.out.println("Good bye!");
                System.exit(0);
            }
            default -> InventoryManagement.printError();
        }
    }

    private static void checkout(Scanner sc, ArrayList<Product> cart, Gson gson) {
        printDiscountMenu();
        BigDecimal totalSum = sumAfterDiscount(sc, cart);
        showCart(cart, totalSum);
        writeCartToJSON(cart, totalSum, gson);
        cart.forEach(removeStock());
        cart.clear();
    }

    private static Consumer<Product> removeStock() {
        return product -> product.setStock(product.getStock() - 1);
    }

    private static BigDecimal sumAfterDiscount(Scanner sc, ArrayList<Product> cart) {
        switch (sc.nextLine()) {
            case "2" -> {
                return halfOfDiscount(getCartSum(cart));
            }
            case "3" -> {
                return christmasDiscount(getCartSum(cart));
            }
            default -> {
                return getCartSum(cart);
            }
        }
    }

    private static void printDiscountMenu() {
        System.out.println("""
                Do you want to add discount?
                1. No discount
                2. Half off
                3. Christmas discount""");
    }

    private static void removeProductToCart(Scanner sc, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);

        try {
            cart.removeIf(checkEan(productToFind));
            System.out.println(productToFind.toUpperCase() + " removed from cart");
        } catch (NumberFormatException e) {
            System.out.println(productToFind.toUpperCase() + " removed from cart");
            cart.removeIf(checkName(productToFind));
        }
    }

    private static Predicate<Product> checkName(String productToFind) {
        return product -> product.getName().equals(productToFind);
    }

    private static Predicate<Product> checkEan(String productToFind) {
        return product -> product.getEan() == Integer.parseInt(productToFind);
    }

    private static void addProductToCart(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);
        try {
            List<Product> findProductByEan = getProductsByEan(products, productToFind);
            findProductByEan.forEach(product -> checkIfInStock(cart, productToFind, findProductByEan, product));
        } catch (NumberFormatException e) {
            List<Product> findProductByName = getProductByName(products, productToFind);
            findProductByName.forEach(product -> checkIfInStock(cart, productToFind, findProductByName, product));
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
        return products.stream().filter(checkEan(productToFind)).toList();
    }

    private static List<Product> getProductByName(ArrayList<Product> products, String productToFind) {
        return products.stream().filter(checkName(productToFind)).toList();
    }

    static void showCart(ArrayList<Product> cart, BigDecimal totalSum) {
        System.out.println("Items in cart:");
        cart.forEach(System.out::println);
        System.out.println("Total price is : $" + totalSum);
    }

    private static void printMenuPos() {
        System.out.println("""
                            
                   Food store
                  Point of sale
                      Menu
                __________________
                1.Show products sorted by name
                2.Add product to cart
                3.Remove product from cart
                4.Show cart
                5.Checkout
                e.Exit
                ------------------""");
    }

    private static BigDecimal christmasDiscount(BigDecimal SumToBeDiscounted) {
        return SumToBeDiscounted.multiply(BigDecimal.valueOf(0.8));
    }

    private static BigDecimal halfOfDiscount(BigDecimal SumToBeDiscounted) {
        return SumToBeDiscounted.multiply(BigDecimal.valueOf(0.5));
    }

    static void writeCartToJSON(ArrayList<Product> cart, BigDecimal totalSum, Gson gson) {
        try {
            FileWriter fileWriter1 = new FileWriter("cart.json");
            gson.toJson(cart, fileWriter1);
            gson.toJson("Total price is : $" + totalSum, fileWriter1);
            fileWriter1.close();
            System.out.println("Saving successful");
        } catch (IOException e) {
            System.out.println("Saving failed");
        }
    }

    private static BigDecimal getCartSum(ArrayList<Product> cart) {
        return cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}