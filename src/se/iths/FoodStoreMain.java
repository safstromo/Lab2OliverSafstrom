package se.iths;

import se.iths.products.Dairy;
import se.iths.products.Fruit;
import se.iths.products.Product;

import java.util.ArrayList;
import java.util.Scanner;



public class FoodStoreMain implements POS,InventoryManagement {
    public static ArrayList<Product> products = new ArrayList<>();//TODO  Import file here
    public static ArrayList<Fruit> fruitArray = new ArrayList<>();
    public static ArrayList<Dairy> dairyArray = new ArrayList<>();
    public static ArrayList<Product> cart = new ArrayList<>();


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        fruitArray = InventoryManagement.importFruitProductDatabase(fruitArray);
        dairyArray = InventoryManagement.importDairyProductDatabase(dairyArray);

        System.out.println("Do you want to start Inventory management or Point of sale?");
        switch (sc.nextLine()){
            case "1" ->  POS.startPOS(sc);
            case "2" -> InventoryManagement.startInventoryManagement(sc,fruitArray,dairyArray);

        }



    }

}
