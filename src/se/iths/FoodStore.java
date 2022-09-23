package se.iths;

import se.iths.products.Dairy;
import se.iths.products.Fruit;
import se.iths.products.Product;

import java.util.ArrayList;
import java.util.Scanner;

public class FoodStore {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Fruit> fruitArray = new ArrayList<>();
        ArrayList<Dairy> dairyArray = new ArrayList<>();


        String menuChoice;
        do {
            printMenu();
            menuChoice = menuSwitch(sc, dairyArray, fruitArray);

        } while (!menuChoice.equals("E"));

    }


    private static String menuSwitch(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        String menuChoice;
        menuChoice = sc.nextLine().toUpperCase();

        switch (menuChoice) {
            case "1" -> addProductMenu(sc, dairyArray, fruitArray);
            case "2" -> printProducts(dairyArray, fruitArray);
            case "3" -> showPrice(sc, dairyArray, fruitArray);
            case "4" -> showStock(dairyArray, fruitArray);
            case "E" -> System.out.println("Good bye!");
            default -> System.out.println("Wrong input, try again");
        }
        return menuChoice;
    }

    private static void addProductMenu(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
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

    private static void addDairyProduct(Scanner sc, ArrayList<Dairy> dairyArray) {

        String tempProductName = getTempProductName(sc);
        Dairy newDairy = new Dairy(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
        dairyArray.add(newDairy);

    }


    private static void addFruitProduct(Scanner sc, ArrayList<Fruit> fruitArray) {
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

    private static void printProducts(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        System.out.println(dairyArray.toString());
        System.out.println(fruitArray.toString());
    }

    private static void showPrice(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {

        ArrayList<Product> allProducts = joinProducts(dairyArray, fruitArray);
        System.out.println("What product do you want to see the price for? (Enter name or EAN)");
        findPrice(allProducts, sc);
    }
    private static void findPrice(ArrayList<Product> allProducts, Scanner sc) {
        String input = sc.nextLine();
        for (Product product : allProducts) {
            try {
                if (product.getEan() == Integer.parseInt(input))
                    System.out.println("The price for: " + product.getName() + " is: " + product.getPrice());
            } catch (NumberFormatException e) {
                if (product.getName().equals(input)) {
                    System.out.println("The price for: " + product.getName() + " is: " + product.getPrice());
                } else System.out.println("The product you are looking for do not exist.");
            }
        }
    }

    private static ArrayList<Product> joinProducts(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {

        ArrayList<Product> allProducts = new ArrayList<>();
        allProducts.addAll(dairyArray);
        allProducts.addAll(fruitArray);
        return allProducts;
    }


    private static void showStock(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
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

