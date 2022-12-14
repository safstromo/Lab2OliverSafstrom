package se.iths;

import com.google.gson.Gson;
import se.iths.products.Product;

import java.util.ArrayList;
import java.util.Scanner;


public class FoodStoreMain {
    public static ArrayList<Product> products = new ArrayList<>();
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
        products = InventoryManagement.importProductDatabase(products, gson);

        while (true) {
            System.out.println("""
                    Do you want to start Inventory management or Point of sale?
                    1. Inventory Management
                    2. Point of sale
                    e. Exit
                    """);
            switch (InventoryManagement.getMenuChoice(sc)) {
                case "1" -> InventoryManagement.startInventoryManagement(getScanner(), products ,gson);
                case "2" -> POS.startPOS(getScanner(),gson);
                case "E" -> {
                    System.out.println("Good bye!");
                    System.exit(0);
                }
                default -> System.out.println("Wrong input try again");

            }


        }
    }

}
