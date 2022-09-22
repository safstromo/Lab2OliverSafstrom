package se.iths;

import se.iths.products.Dairy;
import se.iths.products.Fruit;

import java.util.ArrayList;
import java.util.Scanner;

public class FoodStore {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Fruit> fruitArray = new ArrayList<>();
        ArrayList<Dairy> dairyArray = new ArrayList<>();


        String menuChoise;
        do {
            printMenu();
            menuChoise = menuSwitch(sc, dairyArray, fruitArray);

        } while (!menuChoise.equals("E"));

    }


    private static String menuSwitch(Scanner sc, ArrayList dairyArray, ArrayList fruitArray) {
        String menuChoise;
        menuChoise = sc.nextLine().toUpperCase();

        switch (menuChoise) {
            case "1" -> addProduct(sc, dairyArray,fruitArray);
            case "2" -> showProducts(dairyArray,fruitArray);
            case "3" -> showPrice(dairyArray,fruitArray);
            case "4" -> showStock(dairyArray,fruitArray);
            case "E" -> System.out.println("Good bye!");
            default -> System.out.println("Wrong input, try again");
        }
        return menuChoise;
    }

    private static void addProduct(Scanner sc, ArrayList dairyArray, ArrayList fruitArray) {

        showAddMenu(sc,dairyArray,fruitArray);

    } //TODO

    private static void showAddMenu(Scanner sc, ArrayList dairyArray, ArrayList fruitArray) {

        System.out.println("""
                 
                What do you want to add?
                1.Fruit product
                2.Dairy product""");
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addFruitProduct(sc, fruitArray);
            case "2" -> addDairyProduct(sc, dairyArray);
            case "E" -> System.out.println("Exit to menu");
            default -> System.out.println("Wrong input try again.");
        }

    }

    private static void addDairyProduct(Scanner sc, ArrayList dairyArray) {

        String tempProductName = getTempProductName(sc);
        Dairy newDairy = new Dairy(getTempProductName(sc), getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
        dairyArray.add(newDairy);

    }


    private static void addFruitProduct(Scanner sc, ArrayList fruitArray) {
        String tempProductName = getTempProductName(sc);
        Fruit newFruit = new Fruit(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
        fruitArray.add(newFruit);
    }

    private static int getTempProductPrice(Scanner sc, String tempProductName) {
        System.out.println("Please enter price for " + tempProductName);
        String tempProductPrice = sc.nextLine();
        return Integer.parseInt(tempProductPrice);
    }

    private static int getTempProductEAN(Scanner sc, String tempProductName) {
        System.out.println("Please enter EAN for: " + tempProductName);
        String tempProductEAN = sc.nextLine();
        return Integer.parseInt(tempProductEAN);
    }

    private static String getTempProductName(Scanner sc) {
        System.out.print("Please enter product name: ");
        return sc.nextLine();
    }

    private static void showProducts(ArrayList dairyArray, ArrayList fruitArray) {
        //Todo
    }//TODO

    private static void showPrice(ArrayList dairyArray, ArrayList fruitArray) {
        //Todo

    }//TODO

    private static void showStock(ArrayList dairyArray, ArrayList fruitArray) {
        //Todo
    }//TODO

    private static void printMenu() {
        System.out.println("""
                            
                    Food store
                      Menu
                __________________
                1.Add product
                2.Show products
                3.Show price
                4.Stock
                e.Exit
                ------------------""");
    }
}

