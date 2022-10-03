package se.iths;

import com.google.gson.Gson;
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
        return new Scanner(System.in);
    }

    private static Gson getGson() {
        return new Gson();
    }


    public static void main(String[] args) {
        Scanner sc = getScanner();
        Gson gson = getGson();
        fruitArray = InventoryManagement.importFruitProductDatabase(fruitArray, gson);
        dairyArray = InventoryManagement.importDairyProductDatabase(dairyArray,gson);
        products = InventoryManagement.joinProducts(dairyArray, fruitArray);

        while (true) {
            System.out.println("""
                    Do you want to start Inventory management or Point of sale?
                    1. Point of sale
                    2. Inventory Management
                    e. Exit
                    """);
            switch (InventoryManagement.getMenuChoice(sc)) {
                case "1" -> POS.startPOS(getScanner(),gson);
                case "2" -> InventoryManagement.startInventoryManagement(getScanner(), fruitArray, dairyArray,gson);
                case "E" -> {
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                default -> System.out.println("Wrong input try again");

            }


        }
    }

}
