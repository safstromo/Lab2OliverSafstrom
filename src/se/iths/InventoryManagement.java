package se.iths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import se.iths.products.Dairy;
import se.iths.products.Fruit;
import se.iths.products.Product;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class InventoryManagement {

    static void startInventoryManagement(Scanner sc, ArrayList<Product> products, Gson gson) {
        do {
            printMenuInventoryManagement();
        } while (!menuSwitchInventoryManagement(sc, products, gson).equals("E"));
    }

    static ArrayList<Product> importProductDatabase(ArrayList<Product> products, Gson gson) {
        try {
            FileReader fileReader1 = new FileReader("products.json");
            Type getTypeList = new TypeToken<ArrayList<Fruit>>() {
            }.getType();
            System.out.println("Import of fruit products successful");
            return gson.fromJson(fileReader1, getTypeList);

        } catch (FileNotFoundException e) {
            System.out.println("File of products not found continues without import.");
            return products;
        }
    }

    private static String menuSwitchInventoryManagement(Scanner sc, ArrayList<Product> products, Gson gson) {
        String menuChoice = getMenuChoice(sc);

        switch (menuChoice) {
            case "1" -> addProductMenu(sc, products);
            case "2" -> printProductsSortedByCategory(products);
            case "3" -> searchForProduct(sc, products);
            case "4" -> stockMenu(products, sc);
            case "5" -> removeProduct(products, sc);
            case "E" -> systemExit(products, gson);
            default -> printError();
        }
        return menuChoice;
    }

    private static void systemExit(ArrayList<Product> products, Gson gson) {
        System.out.println("Saving to file, Good bye!");
        writeProductsToJSON(products, gson);
        System.exit(0);
    }

    private static void removeProduct(ArrayList<Product> products, Scanner sc) {
        System.out.println("Enter name or EAN for the product you want to remove.");
        String productToBeDeleted = getTempProductName(sc);
        products.removeIf(checkName(productToBeDeleted));
        try {
            products.removeIf(checkEan(productToBeDeleted));
        } catch (Exception ignored) {
        }
    }

    private static Predicate<Product> checkEan(String productToBeDeleted) {
        return o -> CheckEan(productToBeDeleted, o);
    }

    private static Predicate<Product> checkName(String productToBeDeleted) {
        return o -> CheckName(o.getName(), productToBeDeleted);
    }

    private static void writeProductsToJSON(ArrayList<Product> products, Gson gson) {
        try {
            FileWriter fileWriter = new FileWriter("products.json");
            gson.toJson(products, fileWriter);
            fileWriter.close();
            System.out.println("Saving successful");
        } catch (IOException e) {
            System.out.println("Saving failed");
        }
    }


    static String getMenuChoice(Scanner sc) {
        String menuChoice;
        menuChoice = sc.nextLine().toUpperCase();
        return menuChoice;
    }

    private static void addProductMenu(Scanner sc, ArrayList<Product> products) {
        printAddProductMenu();
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addFruitProduct(sc, products);
            case "2" -> addDairyProduct(sc, products);
            case "E" -> printExit();
            default -> printError();
        }

    }

    private static void printAddProductMenu() {
        System.out.println("""
                 
                What do you want to add?
                1.Fruit product
                2.Dairy product""");
    }

    private static void addDairyProduct(Scanner sc, ArrayList<Product> products) {

        String tempProductName = getTempProductName(sc);
        Dairy newDairy = getNewDairy(sc, tempProductName);

        if (checkIfNotNumber(newDairy)) {
            products.add(newDairy);
        } else {
            System.out.println("Price and EAN needs to be a number try again");
        }

    }

    private static boolean checkIfNotNumber(Dairy newDairy) {
        return checkIfNotNumber(newDairy.getEan(), newDairy.getPrice());
    }

    private static Dairy getNewDairy(Scanner sc, String tempProductName) {
        return new Dairy(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
    }

    private static void addFruitProduct(Scanner sc, ArrayList<Product> products) {
        String tempProductName = getTempProductName(sc);
        Fruit newFruit = getNewFruit(sc, tempProductName);
        if (checkIfNotNumber(newFruit.getEan(), newFruit.getPrice())) {
            products.add(newFruit);
        } else {
            System.out.println("Price and EAN needs to be a number try again");
        }

    }

    private static boolean checkIfNotNumber(int newFruit, BigDecimal newFruit1) {
        return newFruit != -1 && !Objects.equals(newFruit1, BigDecimal.valueOf(-1));
    }

    private static Fruit getNewFruit(Scanner sc, String tempProductName) {
        return new Fruit(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
    }

    static int getTempProductPrice(Scanner sc, String tempProductName) {
        System.out.println("Please enter price for " + tempProductName);
        String tempProductPrice = sc.nextLine();
        try {
            return Integer.parseInt(tempProductPrice);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static int getTempProductEAN(Scanner sc, String tempProductName) {
        System.out.println("Please enter EAN for: " + tempProductName);
        String tempProductEAN = sc.nextLine();
        try {

            return Integer.parseInt(tempProductEAN);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static String getTempProductName(Scanner sc) {
        System.out.print("Please enter product name: ");
        return sc.nextLine().toUpperCase();
    }

    static void printProductsSortedByCategory(ArrayList<Product> products) {
        products.stream()
                .sorted(Comparator.comparing(Product::getCategory))
                .forEach(System.out::println);
    }

    private static void searchForProduct(Scanner sc, ArrayList<Product> products) {

        System.out.println("What product do you want to see? (Enter name or EAN)");
        String input = sc.nextLine().toUpperCase();
        findProductByName(products, input);
        findProductByEAN(products, input);
    }

    private static void findProductByName(ArrayList<Product> allProducts, String input) {
        allProducts.stream()
                .filter(checkName(input))
                .forEach(System.out::println);
    }

    private static void findProductByEAN(ArrayList<Product> allProducts, String input) {
        try {
            allProducts.stream()
                    .filter(checkEan(input))
                    .forEach(System.out::println);
        } catch (NumberFormatException ignored) {
        }
    }


    private static void showStock(ArrayList<Product> products, Scanner sc) {

        System.out.println("What product do you want to see the stock for? (Enter name or EAN)");
        findStock(sc, products);
    }

    private static void findStock(Scanner sc, ArrayList<Product> allProducts) {
        String input = sc.nextLine().toUpperCase();
        for (Product product : allProducts) {
            try {
                if (CheckEan(input, product))
                    printStock(product);
            } catch (NumberFormatException e) {
                if (CheckName(product.getName().toUpperCase(), input)) {
                    printStock(product);
                } else printDoesNotExist();
            }
        }
    }

    private static boolean CheckName(String product, String input) {
        return product.equals(input);
    }

    private static boolean CheckEan(String input, Product product) {
        return product.getEan() == Integer.parseInt(input);
    }

    private static void stockMenu(ArrayList<Product> products, Scanner sc) {
        System.out.println("""
                 
                Do you want to add stock or see stock?
                1.Add stock
                2.See stock""");
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addStock(sc, products);
            case "2" -> showStock(products, sc);
            case "E" -> printExit();
            default -> printError();
        }
    }

    private static void addStock(Scanner sc, ArrayList<Product> products) {
        System.out.println("What product do you want to add the stock for? (Enter name or EAN)");
        String input = sc.nextLine().toUpperCase();

        for (Product product : products) {
            enterStock(sc, input, product);
        }
    }

    private static void enterStock(Scanner sc, String input, Product product) {
        try {
            if (CheckEan(input, product)) {
                askForStock(product);
                trySetStock(sc, product);
            }
        } catch (NumberFormatException e) {
            if (CheckName(product.getName().toUpperCase(), input)) {
                askForStock(product);
                trySetStock(sc, product);
            }
        }
    }

    private static void askForStock(Product product) {
        System.out.println("Enter stock for: " + product.getName());
        System.out.println("How many do you have in stock?");
    }

    private static void trySetStock(Scanner sc, Product product) {
        try {
            product.setStock(Integer.parseInt(sc.nextLine()));
        } catch (Exception e) {
            printDoesNotExist();
        }
    }

    private static void printStock(Product product) {
        System.out.println("The stock for " + product.getName() + " is: " + product.getStock());
    }

    static void printError() {
        System.out.println("Wrong input, try again.");
    }

    private static void printExit() {
        System.out.println("Exit to menu");
    }

    private static void printDoesNotExist() {
        System.out.println("The product you are looking for do not exist.");
    }

    private static void printMenuInventoryManagement() {
        System.out.println("""
                            
                    Food store
                    Inventory
                      Menu
                __________________
                1.Add product
                2.Show list of products sorted by category
                3.Show product of choise
                4.Stock
                5.Remove product
                e.Save and Exit
                ------------------""");
    }
}

