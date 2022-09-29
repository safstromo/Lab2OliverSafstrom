//package se.iths;
//
//import se.iths.products.Dairy;
//import se.iths.products.Product;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import static se.iths.FoodStore.*;
//
//public class POS {
//    public static void main(String[] args) {
//
//
//        Scanner sc = new Scanner(System.in);
//        ArrayList<Product> products = new ArrayList<>(); //TODO  Import file here
//        ArrayList<Product> cart = new ArrayList<>();
//        do {
//            menuSwitch(sc, products, cart);
//        } while (!menuSwitch(sc,products,cart).equals("E"));
//
//
//    }
//
//    private static String menuSwitch(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
//        String menuChoice = getMenuChoice(sc);
//        switch (menuChoice) {
//            case "1" -> addProductToCart(sc, products, cart); // TODO Find product add to arraylist cart
//            case "2" -> removeProductToCart(sc, products, cart); //TODO Find product remove from arraylist cart
//            case "3" -> showCart(cart); // TODO print Arraylist cart and total price
//            case "4" -> checkout(cart); // TODO Print product and prices to recite and save to file, remove products from arraylist cart.
//            case "E" -> System.out.println("Good bye!");
//            default -> FoodStore.printError();
//        }
//        return menuChoice;
//    }
//
//    private static void addProductToCart(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart) {
//        cart.add(products.stream().findAny().equals(getTempProductName(sc)));
//    }
//
//    private static void showCart(ArrayList products) {
//        System.out.println(products.forEach(o -> o.getName));
//        System.out.println(products.forEach(o -> ));
//
//
//    }
//}
