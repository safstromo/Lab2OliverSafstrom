package se.iths;

import java.util.Scanner;

public class FoodStore {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String menuChoise;
        do {
            printMenu();
            menuChoise = menuSwitch(sc);
        } while (!menuChoise.equals("E"));
    }



    private static String menuSwitch(Scanner sc) {
        String menuChoise;
        menuChoise = sc.nextLine().toUpperCase();

        switch (menuChoise) {
            case "1" -> addProduct();
            case "2" -> showProducts();
            case "3" -> showPrice();
            case "4" -> showStock();
            case "E" -> System.out.println("Good bye!");
            default -> System.out.println("Wrong input, try again");
        }
        return menuChoise;
    }

    private static void addProduct() {
        } //TODO

    private static void showProducts() {
        //Todo
    }//TODO

    private static void showPrice() {
        //Todo

    }//TODO

    private static void showStock() {
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

