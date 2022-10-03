package se.iths;

import se.iths.products.Dairy;
import se.iths.products.Fruit;
import se.iths.products.Product;

import java.util.ArrayList;
import java.util.Scanner;


public class FoodStoreMain implements POS, InventoryManagement {
    public static ArrayList<Product> products = new ArrayList<>();//TODO  Import file here
    public static ArrayList<Fruit> fruitArray = new ArrayList<>();
    public static ArrayList<Dairy> dairyArray = new ArrayList<>();
    public static ArrayList<Product> cart = new ArrayList<>();

    public static Scanner getScanner() {
        Scanner sc = new Scanner(System.in);
        return sc;
    }


    public static void main(String[] args) {
        Scanner sc = getScanner();

        fruitArray = InventoryManagement.importFruitProductDatabase(fruitArray);
        dairyArray = InventoryManagement.importDairyProductDatabase(dairyArray);
        products = InventoryManagement.joinProducts(dairyArray,fruitArray);

        while (true) {
            System.out.println("""
                    Do you want to start Inventory management or Point of sale?
                    1. Point of sale
                    2. Inventory Management
                    e. Exit
                    """);
            switch (sc.next()) {
                case "1" -> POS.startPOS(getScanner());
                case "2" -> InventoryManagement.startInventoryManagement(getScanner(), fruitArray, dairyArray);
                case "e", "E" -> {
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Wrong input try again");
                }

            }


        }
    }

}
