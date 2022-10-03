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
import java.util.ArrayList;
import java.util.Scanner;

public interface InventoryManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Fruit> fruitArray = new ArrayList<>();
        ArrayList<Dairy> dairyArray = new ArrayList<>();
        ArrayList<Product> cartArray = new ArrayList<>();
        fruitArray = importFruitProductDatabase(fruitArray);
        dairyArray = importDairyProductDatabase(dairyArray);

        startInventoryManagement(sc, fruitArray, dairyArray);

    }
    static void startInventoryManagement(Scanner sc, ArrayList<Fruit> fruitArray, ArrayList<Dairy> dairyArray) {
        do {
            printMenuInventoryManagement();
        } while (!menuSwitchInventoryManagement(sc, dairyArray, fruitArray).equals("E"));
    }

    static ArrayList<Fruit> importFruitProductDatabase(ArrayList<Fruit> fruitArray) {
        try {
            FileReader fileReader1 = new FileReader("fruits.json");
            Gson gson = new Gson();

            Type getTypeList = new TypeToken<ArrayList<Fruit>>() {
            }.getType();


            System.out.println("Import of fruit products successful");
            return gson.fromJson(fileReader1, getTypeList);


        } catch (FileNotFoundException e) {
            System.out.println("File of products not found continues without import.");
            return fruitArray;
        }
    }

    static ArrayList<Dairy> importDairyProductDatabase(ArrayList<Dairy> dairyArray) {
        try {
            FileReader fileReader1 = new FileReader("dairies.json");
            Gson gson = new Gson();

            Type getTypeList = new TypeToken<ArrayList<Dairy>>() {
            }.getType();


            System.out.println("Import of dairy products successful");
            return gson.fromJson(fileReader1, getTypeList);


        } catch (FileNotFoundException e) {
            System.out.println("File of products not found continues without import.");
            return dairyArray;
        }
    }


    private static String menuSwitchInventoryManagement(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        String menuChoice = getMenuChoice(sc);

        switch (menuChoice) {
            case "1" -> addProductMenu(sc, dairyArray, fruitArray);
            case "2" -> printProducts(dairyArray, fruitArray);
            case "3" -> showPrice(sc, dairyArray, fruitArray);
            case "4" -> stockMenu(dairyArray, fruitArray, sc);
            case "5" -> removeProduct(dairyArray, fruitArray, sc);
            case "E" -> {
                System.out.println("Saving to file, Good bye!");
                writeToJSON(dairyArray, fruitArray);
            }
            default -> printError();
        }
        return menuChoice;
    }

    private static void removeProduct(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray, Scanner sc) {
        System.out.println("Enter name or EAN for the product you want to remove.");
        String productToBeDeleted = getTempProductName(sc);
        dairyArray.removeIf(o -> o.getName().equals(productToBeDeleted));
        fruitArray.removeIf(o -> o.getName().equals(productToBeDeleted));
        try {
            dairyArray.removeIf(o -> o.getEan() == Integer.parseInt(productToBeDeleted));
            fruitArray.removeIf(o -> o.getEan() == Integer.parseInt(productToBeDeleted));

        }catch (Exception e){}
    }

    private static void writeToJSON(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        try {
            Gson gson = new Gson();
            FileWriter fileWriter1 = new FileWriter("dairies.json");
            FileWriter fileWriter2 = new FileWriter("fruits.json");
            gson.toJson(dairyArray, fileWriter1);
            gson.toJson(fruitArray, fileWriter2);
            fileWriter1.close();
            fileWriter2.close();
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

    private static void addProductMenu(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        System.out.println("""
                 
                What do you want to add?
                1.Fruit product
                2.Dairy product""");
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addFruitProduct(sc, fruitArray);
            case "2" -> addDairyProduct(sc, dairyArray);
            case "E" -> printExit();
            default -> printError();
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

    private static void printProducts(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        System.out.println(joinProducts(dairyArray, fruitArray));
    }

    private static void showPrice(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {

        System.out.println("What product do you want to see the price for? (Enter name or EAN)");
        findPrice(joinProducts(dairyArray, fruitArray), sc);
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

    private static ArrayList<Product> joinProducts(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {

        ArrayList<Product> allProducts = new ArrayList<>();
        allProducts.addAll(dairyArray);
        allProducts.addAll(fruitArray);
        return allProducts;
    }

    private static void showStock(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray, Scanner sc) {

        System.out.println("What product do you want to see the stock for? (Enter name or EAN)");
        findStock(sc, joinProducts(dairyArray, fruitArray));


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

    private static void stockMenu(ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray, Scanner sc) {
        System.out.println("""
                 
                Do you want to add stock or see stock?
                1.Add stock
                2.See stock""");
        switch (sc.nextLine().toUpperCase()) {
            case "1" -> addStock(sc, dairyArray, fruitArray);
            case "2" -> showStock(dairyArray, fruitArray, sc);
            case "E" -> printExit();
            default -> printError();
        }
    }

    private static void addStock(Scanner sc, ArrayList<Dairy> dairyArray, ArrayList<Fruit> fruitArray) {
        System.out.println("What product do you want to add the stock for? (Enter name or EAN)");
        String input = sc.nextLine().toUpperCase();

        for (Product product : dairyArray) {
            enterStock(sc, input, product);
        }
        for (Product product : fruitArray) {
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

