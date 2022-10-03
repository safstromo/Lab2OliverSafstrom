package se.iths;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import se.iths.products.Dairy;
import se.iths.products.Products;
import se.iths.products.Product;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public interface InventoryManagement {

    static void startInventoryManagement(Scanner sc, ArrayList<Product> products, Gson gson) {
        do {
            printMenuInventoryManagement();
        } while (!menuSwitchInventoryManagement(sc, products, gson).equals("E"));
    }

    static ArrayList<Product> importProductDatabase(ArrayList<Product> products, Gson gson) {
        try {
            FileReader fileReader1 = new FileReader("products.json");
            Type getTypeList = new TypeToken<ArrayList<Products>>() {
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
            case "2" -> printProducts(products);
            case "3" -> showPrice(sc, products);
            case "4" -> stockMenu(products, sc);
            case "5" -> removeProduct(products, sc);
            case "E" -> {
                System.out.println("Saving to file, Good bye!");
                writeProductsToJSON(products, gson);
                System.exit(0);
            }
            default -> printError();
        }
        return menuChoice;
    }

    private static void removeProduct(ArrayList<Product> products, Scanner sc) {
        System.out.println("Enter name or EAN for the product you want to remove.");
        String productToBeDeleted = getTempProductName(sc);
        products.removeIf(o -> o.getName().equals(productToBeDeleted));
        try {
            products.removeIf(o -> o.getEan() == Integer.parseInt(productToBeDeleted));
        } catch (Exception ignored) {
        }
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

    static void writeCartToJSON(ArrayList<Product> cart, Gson gson) {
        try {
            FileWriter fileWriter1 = new FileWriter("cart.json");
            gson.toJson(cart, fileWriter1);
            BigDecimal sum = cart.stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            gson.toJson("Total price is : $" + sum, fileWriter1);
            fileWriter1.close();
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
        System.out.println("""
                 
                What do you want to add?
                1.Fruit product
                2.Dairy product""");
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addFruitProduct(sc, products);
            case "2" -> addDairyProduct(sc, products);
            case "E" -> printExit();
            default -> printError();
        }

    }

    private static void addDairyProduct(Scanner sc, ArrayList<Product> products) {

        String tempProductName = getTempProductName(sc);
        Dairy newDairy = new Dairy(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
        products.add(newDairy);

    }

    private static void addFruitProduct(Scanner sc, ArrayList<Product> products) {
        String tempProductName = getTempProductName(sc);
        Products newFruit = new Products(tempProductName, getTempProductPrice(sc, tempProductName), getTempProductEAN(sc, tempProductName));
        products.add(newFruit);

    }

    static int getTempProductPrice(Scanner sc, String tempProductName) {
        System.out.println("Please enter price for " + tempProductName);
        String tempProductPrice = sc.nextLine();
        return Integer.parseInt(tempProductPrice);
    }

    static int getTempProductEAN(Scanner sc, String tempProductName) {
        System.out.println("Please enter EAN for: " + tempProductName);
        String tempProductEAN = sc.nextLine();
        return Integer.parseInt(tempProductEAN);
    }

    static String getTempProductName(Scanner sc) {
        System.out.print("Please enter product name: ");
        return sc.nextLine().toUpperCase();
    }

    private static void printProducts(ArrayList<Product> products) {
        System.out.println(products);
    }

    private static void showPrice(Scanner sc, ArrayList<Product> products) {

        System.out.println("What product do you want to see the price for? (Enter name or EAN)");
        findPrice(products, sc);
    }

    private static void findPrice(ArrayList<Product> allProducts, Scanner sc) {
        String input = sc.nextLine().toUpperCase();
        for (Product product : allProducts) {
            try {
                if (product.getEan() == Integer.parseInt(input))
                    printPrice(product);
            } catch (NumberFormatException e) {
                if (product.getName().toUpperCase().equals(input)) {
                    printPrice(product);
                } else printDoesNotExist();
            }
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
                if (product.getEan() == Integer.parseInt(input))
                    printStock(product);
            } catch (NumberFormatException e) {
                if (product.getName().toUpperCase().equals(input)) {
                    printStock(product);
                } else printDoesNotExist();
            }
        }
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
            if (product.getEan() == Integer.parseInt(input)) {
                askForStock(product);
                trySetStock(sc, product);
            }
        } catch (NumberFormatException e) {
            if (product.getName().toUpperCase().equals(input)) {
                askForStock(product);
                trySetStock(sc, product);
            } else printDoesNotExist();
        }
    }

    private static void printPrice(Product product) {
        System.out.println("The price for: " + product.getName() + " is: " + product.getPrice());
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

    private static void trySetStock(Scanner sc, Product product) {
        try {
            product.setStock(Integer.parseInt(sc.nextLine()));
        } catch (Exception e) {
            System.out.println("Something went wrong try again.");
        }
    }

    private static void askForStock(Product product) {
        System.out.println("Enter stock for: " + product.getName());
        System.out.println("How many do you have in stock?");
    }

    private static void printMenuInventoryManagement() {
        System.out.println("""
                            
                    Food store
                    Inventory
                      Menu
                __________________
                1.Add product
                2.Show products
                3.Show price
                4.Stock
                5.Remove product
                e.Exit
                ------------------""");
    }
}

