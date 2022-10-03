package se.iths;

import se.iths.products.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static se.iths.InventoryManagement.*;

public interface POS {


    public static void main(String[] args) {


        Scanner sc = new Scanner(System.in);

        startPOS(sc);


    }

    static void startPOS(Scanner sc) {
        do {
            printMenuPos();
            menuSwitchPos(sc, FoodStoreMain.products, FoodStoreMain.cart);
        } while (!menuSwitchPos(sc, FoodStoreMain.products, FoodStoreMain.cart).equals("E"));
    }

    public static String menuSwitchPos(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
        String menuChoice = getMenuChoice(sc);
        switch (menuChoice) {
            case "1" -> addProductToCart(sc, products, cart);
            case "2" -> removeProductToCart(sc, cart);
            case "3" -> showCart(cart);
            case "4" ->
                    checkout(cart); // TODO Print product and prices to recite and save to file, remove products from arraylist cart.
            case "E" -> System.out.println("Good bye!");
            default -> InventoryManagement.printError();
        }
        return menuChoice;
    }

    private static void checkout(ArrayList<Product> cart) {
    }

    private static void removeProductToCart(Scanner sc, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);
        List<Product> findProductByName = getProductByName(cart, productToFind);
        List<Product> findProductByEan = getProductsByEan(cart, productToFind);
        System.out.println(productToFind.toUpperCase() + "removed from cart");
        cart.removeAll(findProductByName);
        cart.removeAll(findProductByEan);
    }

    private static void addProductToCart(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
        String productToFind = getTempProductName(sc);
        List<Product> findProductByName = getProductByName(products, productToFind);
        List<Product> findProductByEan = getProductsByEan(products, productToFind);
        cart.addAll(findProductByName);
        cart.addAll(findProductByEan);
        System.out.println(productToFind.toUpperCase() + "Added to cart");

    }

    private static List<Product> getProductsByEan(ArrayList<Product> products, String productToFind) {
        return products.stream().filter(p -> p.getEan() == Integer.parseInt(productToFind)).toList();
    }

    private static List<Product> getProductByName(ArrayList<Product> products, String productToFind) {
        return products.stream().filter(p -> p.getName().equals(productToFind)).toList();
    }

    private static void showCart(ArrayList<Product> cart) {
        BigDecimal sum = cart.stream().map(p -> p.getPrice().add(p.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
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
