package presentation;

import business.ProductDTO;
import business.ProductManager;
import business.ShopManager;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Controller {

    private static UIManager uiManager;
    private static ProductManager productManager;
    private static ShopManager shopManager;

    public Controller(UIManager uiManager, ProductManager productManager, ShopManager shopManager) {
        Controller.uiManager = uiManager;
        Controller.productManager = productManager;
        Controller.shopManager = shopManager;
    }

    public void run() {

        //Greeting and check if file opens

        uiManager.showMessage("        ________      ____\n" +
                            "  ___  / / ____/___  / __/_______\n" +
                           " / _ \\/ / / " + "  / __ \\/ /_/ ___/ _ \\\n" +
                         "/  __/ / /___/ /_/ / __/ /\t/  __/\n" +
                         "\\___/_/\\____/\\____/_/ /_/\t" + "\\___/\n");

        uiManager.showMessage("Welcome to elCofre Digital Shopping Experiences.\n");
        uiManager.showMessage("Verifying local files...");
        if (!shopManager.ifFileExists()) {
            uiManager.showMessage("Error: The shops.json file can’t be accessed.");
            uiManager.showMessage("Shutting down...");
            return;
        }
        uiManager.showMessage("Starting program...");

        //Main menu

        while (true) {

            uiManager.showMessage("""
                                    \n\t1) Manage Products
                                    \t2) Manage Shops
                                    \t3) Search Products
                                    \t4) List Shops
                                    \t5) Your Cart
                                    
                                    \t6) Exit
                                    """);

            int option = 0;
            boolean error = true;
            while (error) {
                try {
                    option = uiManager.askForInt("Choose a Digital Shopping Experience: ");
                    error = false;
                } catch (InputMismatchException inputMismatchException) {
                    uiManager.showMessage("Error: the chosen experience should be a number from 1 to 6.");
                    uiManager.scannerNext();
                }
            }

            switch (option) {
                case 1:
                    productManagement();
                    break;

                case 2:
                    shopManagement();
                    break;

                case 3:
                    productSearch();
                    break;

                case 4:
                    shopList();
                    break;

                case 5:
                    cartManagement();
                    break;

                case 6:
                    uiManager.showMessage("We hope to see you again!");
                    return;

                default:
                    uiManager.showMessage("Error: the chosen experience should be a number from 1 to 6.");
                    break;
            }

        }

    }
    private void productManagement() {

        uiManager.showMessage("""
                                \n\t1) Create a Product
                                \t2) Remove a Product
                                    
                                \t3) Back""");

        int option = 0;
        boolean error = true;
        while (error) {
            try {
                option = uiManager.askForInt("Choose an option: ");
                error = false;
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the chosen option should be a number from 1 to 3.");
                uiManager.scannerNext();
            }
        }

        switch (option) {

            case 1:
                productCreation();
                break;

            case 2:
                productRemoval();
                break;

            case 3:
                return;

            default:
                uiManager.showMessage("Error: the chosen option should be a number from 1 to 3.");
                break;

        }

    }

    private void productCreation() {

        //Getting the name

        String name = uiManager.askForString("Please enter the product's name: ");
        if (!productManager.checkIfUnique(name)) {
            uiManager.showMessage("Error: the product's name is not unique.");
        }

        //Getting the brand (using the commons text library)

        String brand = uiManager.askForString("Please enter the product's brand: ");
        String brandCapitalized = WordUtils.capitalizeFully(brand);

        //Getting the mrp

        double mrp = 0;
        boolean error = true;
        while (error) {
            try {
                mrp = uiManager.askForDouble("Please enter the product’s maximum retail price: ");
                error = false;
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the maximum retail price must be a floating-point number.");
                uiManager.scannerNext();
            }
        }

        //Getting the category

        String chosenCategory = "D";
        String productCategory = null;

        while (chosenCategory.charAt(0) != 'A' && chosenCategory.charAt(0) != 'B' && chosenCategory.charAt(0) != 'C') {
            do {
                uiManager.showMessage("""
                        The system supports the following product categories:
                                                            
                        \tA) General
                        \tB) Reduced Taxes
                        \tC) Superreduced Taxes
                        """);
                chosenCategory = uiManager.askForString("Please pick the product’s category: ");
                if (chosenCategory.length() > 1) uiManager.showMessage("Error: the chosen category is a letter in the range of A to C.");
            } while (chosenCategory.length() > 1);


            switch (chosenCategory.charAt(0)) {

                case 'A':
                    productCategory = "GENERAL";
                    break;
                case 'B':
                    productCategory = "REDUCED";
                    break;
                case 'C':
                    productCategory = "SUPER_REDUCED";
                    break;
                default:
                    uiManager.showMessage("Error: the chosen category is a letter in the range of A to C.");

            }

        }

        if (!productManager.createProduct(name, brandCapitalized, mrp, productCategory)) {
            uiManager.showMessage("The product was not added to the system.");
            return;
        }
        uiManager.showMessage("The product \"" + name + "\" by \"" + brand + "\" was added to the system.");

    }

    private void productRemoval() {

        uiManager.showMessage("These are the currently available products:\n");

        ArrayList<ProductDTO> productDTOs = productManager.getProductDTOs();

        int i = 1;
        int productIndex = 0;

        do {

            boolean error = true;
            while (error) {
                try {
                    for (i = 1; i <= productDTOs.size(); ++i) {
                        uiManager.showMessage("\t" + i + ") \"" + productDTOs.get(i - 1).getName() + "\" by \"" +
                                productDTOs.get(i - 1).getBrand() + "\"");
                    }

                    uiManager.showMessage("\n\t" + i + ") Back\n");
                    productIndex = uiManager.askForInt("Which one would you like to remove? ");
                    error = false;
                } catch (InputMismatchException inputMismatchException) {
                    uiManager.showMessage("Error: the chosen option should be a number from 1 to " + i + ".\n");
                    uiManager.scannerNext();
                }
            }

            if (productIndex <= 0 || productIndex > i) uiManager.showMessage("Error: the chosen option should be a " +
                    "number from 1 to " + i + ".\n");

        } while (productIndex <= 0 || productIndex > i);

        if (productIndex == i) return;

        if (productManager.deleteProduct(productDTOs.get(productIndex-1).getName())) {
            uiManager.showMessage("\"" + productDTOs.get(productIndex-1).getName() + "\" by \"" +
                    productDTOs.get(productIndex-1).getBrand() + "\" has been withdrawn from sale.");
        }


    }

    private void shopManagement() {

    }

    private void productSearch() {

    }
    private void shopList() {

    }

    private void cartManagement() {
    }

}