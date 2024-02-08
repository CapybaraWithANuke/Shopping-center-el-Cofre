package presentation;

import business.ProductDTO;
import business.ProductManager;
import business.ShopDTO;
import business.ShopManager;
import org.apache.commons.text.WordUtils;
import persistence.ProductInShop;
import persistence.Review;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Controller {

    private static UIManager uiManager;
    private static ProductManager productManager;
    private static ShopManager shopManager;
    private Cart cart;

    public Controller(UIManager uiManager, ProductManager productManager, ShopManager shopManager) {
        Controller.uiManager = uiManager;
        Controller.productManager = productManager;
        Controller.shopManager = shopManager;
        this.cart = new Cart();
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
        if (productManager.ifNameInSystem(name)) {
            uiManager.showMessage("Error: the product's name is not unique.");
            return;
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
                if (mrp >= 0) {
                    error = false;
                }
                else {
                    uiManager.showMessage("Error: the maximum retail price must be a positive number.");
                    uiManager.scannerNext();
                }
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
                if (chosenCategory.length() > 1) uiManager.showMessage("Error: the chosen category should be a single letter.");
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
                    uiManager.showMessage("Error: the chosen category should be a letter in the range of A to C.");

            }

        }

        if (!productManager.createProduct(name, brandCapitalized, mrp, productCategory)) {
            uiManager.showMessage("The product was not added to the system.");
            return;
        }
        uiManager.showMessage("The product \"" + name + "\" by \"" + brandCapitalized + "\" was added to the system.");

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

        //TODO: Add the deletion from shops

    }

    private void shopManagement() {
        uiManager.showMessage("""
                                \n\t1) Create a Shop
                                \t2) Expand a Shop's Catalogue
                                \t3) Reduce a Shop's Catalogue
                                    
                                \t4) Back""");

        int option = 0;
        boolean error = true;
        while (error) {
            try {
                option = uiManager.askForInt("Choose an option: ");
                error = false;
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the chosen option should be a number from 1 to 4.");
                uiManager.scannerNext();
            }
        }

        switch (option) {

            case 1:
                shopCreation();
                break;

            case 2:
                catalogueExpansion();
                break;

            case 3:
                catalogueReduction();
                break;
            case 4:
                return;

            default:
                uiManager.showMessage("Error: the chosen option should be a number from 1 to 4.");
                break;

        }
    }

    private void shopCreation() {

        //Getting the name

        String name = uiManager.askForString("Please enter the shop's name: ");
        if (shopManager.ifNameInSystem(name)) {
            uiManager.showMessage("Error: the shop's name is not unique.");
            return;
        }

        //Getting the description

        String description = uiManager.askForString("Please enter the shop's description: ");

        //Getting the founding year

        short founding_year = 0;
        boolean error = true;
        while (error) {
            try {
                founding_year = uiManager.askForShort("Please enter the shop’s founding year: ");
                if (founding_year > 0) {
                    error = false;
                }
                else {
                    uiManager.showMessage("Error: the founding year must be a positive number.");
                    uiManager.scannerNext();
                }
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the founding year must be a small number.");
                uiManager.scannerNext();
            }
        }

        String chosenModel = "D";
        String shopModel = null;

        while (chosenModel.charAt(0) != 'A' && chosenModel.charAt(0) != 'B' && chosenModel.charAt(0) != 'C') {
            do {
                uiManager.showMessage("""
                        The system supports the following business models:
                                                            
                        \tA) Maximum Benefits
                        \tB) Loyalty
                        \tC) Sponsored
                        """);
                chosenModel = uiManager.askForString("Please pick the shop’s business model: ");
                if (chosenModel.length() > 1) uiManager.showMessage("Error: the chosen model should be a single letter.");
            } while (chosenModel.length() > 1);


            switch (chosenModel.charAt(0)) {

                case 'A':
                    shopModel = "MAX_PROFIT";
                    break;
                case 'B':
                    shopModel = "LOYALTY";
                    break;
                case 'C':
                    shopModel = "SPONSORED";
                    break;
                default:
                    uiManager.showMessage("Error: the chosen model should be letter in the range of A to C.");

            }

        }

        if (!shopManager.createShop(name, description, founding_year, shopModel)) {
            uiManager.showMessage("The shop was not added to the system.");
            return;
        }
        uiManager.showMessage("\"" + name + "\" is now a part of the elCofre family.");



    }

    private void catalogueExpansion() {

        String shop_name = uiManager.askForString("\nPlease enter the shop's name: ");
        if (!shopManager.ifNameInSystem(shop_name)) {
            uiManager.showMessage("Error: the shop doesn't exist.");
            return;
        }

        String product_name = uiManager.askForString("Please enter the product's name: ");
        if (!productManager.ifNameInSystem(product_name)) {
            uiManager.showMessage("Error: the product doesn't exist.");
            return;
        }
        if (shopManager.isProductInShop(shop_name, product_name)) {
            uiManager.showMessage("Error: the product is already sold at this shop.");
            return;
        }

        double price = 0;
        boolean error = true;
        while (error) {
            try {
                price = uiManager.askForDouble("Please enter the product's price at this shop: ");
                if (productManager.isPriceLessThanMRP(product_name, price)) {
                    error = false;
                }
                else {
                    uiManager.showMessage("Error: the price for the product should be less than or equal to MRP.");
                }
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the product's price should be a floating-point number.");
                uiManager.scannerNext();
            }
        }

        if (shopManager.addProductToCatalogue(shop_name, product_name, price)) {
            uiManager.showMessage("\"" + product_name + "\" by \"" + productManager.getBrand(product_name) + "\" is now being sold at \"" + shop_name + "\"");
        }
        else {
            uiManager.showMessage("Error: product not added to the shop's catalogue.");
        }

    }

    private void catalogueReduction() {

        String shop_name = uiManager.askForString("\nPlease enter the shop's name: ");
        if (!shopManager.ifNameInSystem(shop_name)) {
            uiManager.showMessage("Error: the shop doesn't exist.");
            return;
        }

        ArrayList<ProductDTO> products = shopManager.getProductDTOs(shop_name);

        uiManager.showMessage("\nThe shop sells the following products:\n");

        int i;
        for (i = 0; i < products.size(); ++i)
            uiManager.showMessage("\t" + (i+1) + ") \"" + products.get(i).getName() + "\" by \"" + products.get(i).getBrand() + "\"");

        uiManager.showMessage("\n\t" + (i+1) + ") Back\n");

        int remove = 0;
        boolean error = true;
        while (error) {
            try {
                remove = uiManager.askForInt("Which one would you like to remove? ");
                if (remove > 0 && remove <= (i+1)) {
                    error = false;
                }
                else {
                    uiManager.showMessage("Error: the chosen option should be in the range of 1 to " + (i+1) + ".");
                }
            } catch (InputMismatchException inputMismatchException) {
                uiManager.showMessage("Error: the chosen option should be an integer.");
                uiManager.scannerNext();
            }
        }

        if (remove == i+1) return;

        if (shopManager.deleteProduct(shop_name, remove-1))
            uiManager.showMessage("\"" + productManager.getProductDTO(products.get(i-1).getName()).getName() + "\" by \""
                    + productManager.getProductDTO(products.get(i-1).getName()).getBrand() + "\" is no longer being sold at " +
                    "\"" + shop_name + "\"");
        else {
            uiManager.showMessage("Error: product not removed.");
        }

    }


    private void productSearch() {
        String query = uiManager.askForString("\nEnter your query: ");

        ArrayList<ProductDTO> searchResults = productManager.searchProducts(query);

        if (searchResults.isEmpty()) {
            uiManager.showMessage("No products found for the given query.");
            return;
        }

        uiManager.showMessage("\nThe following products were found:\n");

        int index = 1;
        for (ProductDTO product : searchResults) {
            uiManager.showMessage("\t" + index + ") \"" + product.getName() + "\" by \"" +
                    product.getBrand() + "\"");

            ArrayList<String> shops = shopManager.getShopsForProduct(product.getName());
            if (!shops.isEmpty()) {
                uiManager.showMessage("\t\tSold at:");
                for (String shopInfo : shops) {
                    uiManager.showMessage("\t\t\t- " + shopInfo);
                }
            } else {
                uiManager.showMessage("\t\tThis product is not currently being sold in any shops.");
            }

            index++;
        }

        uiManager.showMessage("\n\t"+index + ") Back");

        int selection = 0;
        boolean error = true;

        while (error) {
            try {
                selection = uiManager.askForInt("\nWhich one would you like to review? ");
                if (selection >= 1 && selection <= index) {
                    error = false;
                } else if (selection == index) {
                    return; // User selected "Back"
                } else {
                    uiManager.showMessage("Error: Please choose a number between 1 and " + index + ".");
                }
            } catch (InputMismatchException e) {
                uiManager.showMessage("Error: Please enter a valid number.");
                uiManager.scannerNext();
            }
        }

        if (selection != index) {
            // Product selected, perform additional actions if needed
            // (e.g., read reviews, review the product)
            handleProductSelection(searchResults.get(selection - 1));
        }
    }

    private void handleProductSelection(ProductDTO selectedProduct) {
        uiManager.showMessage("\n\t1) Read Reviews");
        uiManager.showMessage("\t2) Review Product");

        int option = uiManager.askForInt("\nChoose an option: ");

        switch (option) {
            case 1:
                readReviews(selectedProduct);
                break;
            case 2:
                reviewProduct(selectedProduct);
                break;
            default:
                uiManager.showMessage("Invalid option.");
                break;
        }
    }

    private void reviewProduct(ProductDTO selectedProduct) {
        short stars = convertStarsToNumber(uiManager.askForString("\nPlease rate the product (1-5 stars): "));

        String comment = uiManager.askForString("Please add a comment to your review: ");

        productManager.addReview(selectedProduct.getName(), stars, comment);

        uiManager.showMessage("\nThank you for your review of \"" + selectedProduct.getName() +
                "\" by \"" + selectedProduct.getBrand() + "\".");
    }
    private short convertStarsToNumber(String starsInput) {
        int totalStars = 0;

        for (char c : starsInput.toCharArray()) {
            if (c == '*') {
                totalStars++;
            }
        }
        return (short) totalStars;
    }

    private void readReviews(ProductDTO selectedProduct) {
        ArrayList<Review> reviews = productManager.getReviews(selectedProduct.getName());

        if (reviews != null) {
            uiManager.showMessage("\nThese are the reviews for \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() + "\":\n");

            for (Review review : reviews) {
                uiManager.showMessage("\t" + review.getStars() + "* " + review.getComment());
            }

            double averageRating = productManager.calculateAverageRating(reviews);
            uiManager.showMessage("\n\tAverage rating: " + String.format("%.2f", averageRating) + "*");
        } else {
            uiManager.showMessage("Error: Unable to retrieve reviews for \"" + selectedProduct.getName() + "\".");
        }
    }


    private void shopList() {
        ArrayList<String> shopNames = shopManager.getAllShopNames();

        uiManager.showMessage("\nThe elCofre family is formed by the following shops:\n");

        int index = 1;
        for (String shopName : shopNames) {
            uiManager.showMessage("\t" + index + ") " + shopName);
            index++;
        }
        uiManager.showMessage("\n\t" + index + ") Back");

        int shopChoice = 0;
        boolean error = true;

        while (error) {
            try {
                shopChoice = uiManager.askForInt("\nWhich catalogue do you want to see? ");
                if (shopChoice >= 1 && shopChoice <= index) {
                    error = false;
                } else if (shopChoice == index) {
                    return; // User selected "Back"
                } else {
                    uiManager.showMessage("Error: Please choose a number between 1 and " + index + ".");
                }
            } catch (InputMismatchException e) {
                uiManager.showMessage("Error: Please enter a valid number.");
                uiManager.scannerNext();
            }
        }

        if (shopChoice != index) {
            // Shop selected, show details and catalogue
            handleShopSelection(shopNames.get(shopChoice - 1));
        }
    }

    private void handleShopSelection(String selectedShopName) {
        ShopDTO shopDetailsDTO = shopManager.getShopDetails(selectedShopName);

        if (shopDetailsDTO != null) {
            uiManager.showMessage("\n" + shopDetailsDTO.getName() + " - Since " + shopDetailsDTO.getSince());
            uiManager.showMessage(shopDetailsDTO.getDescription());
        } else {
            uiManager.showMessage("\nError: Unable to retrieve details for the selected shop.");
            return;
        }

        ArrayList<ProductDTO> productDTOs = shopManager.getProductDTOsShop(selectedShopName);

        if (productDTOs != null) {
            uiManager.showMessage("\nProducts in the catalogue of " + selectedShopName + ":\n");

            int productIndex = 1;
            for (ProductDTO product : productDTOs) {
                uiManager.showMessage("\t" + productIndex + ") \"" + product.getName() + "\" by \"" +
                        product.getBrand() + "\"\n\t\tPrice: " + shopManager.getProductPrice(selectedShopName, product.getName()));
                productIndex++;
            }

            uiManager.showMessage("\n\n\t" + productIndex + ") Back");

            int selection = 0;
            boolean selectionError = true;

            while (selectionError) {
                try {
                    selection = uiManager.askForInt("\nWhich one are you interested in? ");
                    if (selection >= 1 && selection <= productIndex) {
                        selectionError = false;
                    } else if (selection == productIndex) {
                        return; // User selected "Back"
                    } else {
                        uiManager.showMessage("Error: Please choose a number between 1 and " + productIndex + ".");
                    }
                } catch (InputMismatchException e) {
                    uiManager.showMessage("Error: Please enter a valid number.");
                    uiManager.scannerNext();
                }
            }

            if (selection != productIndex) {
                handleProductSelectionInShop(productDTOs.get(selection - 1), selectedShopName);
            }
        } else {
            uiManager.showMessage("Error: Unable to retrieve products for the selected shop.");
        }
    }

    private void handleProductSelectionInShop(ProductDTO selectedProduct, String selectedShopName) {
        uiManager.showMessage("\n\t1) Read Reviews");
        uiManager.showMessage("\t2) Review Product");
        uiManager.showMessage("\t3) Add to Cart");

        int option = uiManager.askForInt("\nChoose an option: ");

        switch (option) {
            case 1:
                readReviewsForShop(selectedProduct, selectedShopName);
                break;
            case 2:
                reviewProductForShop(selectedProduct, selectedShopName);
                break;
            case 3:
                addToCart(selectedProduct, selectedShopName);
                break;
            default:
                uiManager.showMessage("Invalid option.");
                break;
        }
    }

    private void readReviewsForShop(ProductDTO selectedProduct, String selectedShopName) {
        ArrayList<Review> reviews = productManager.getReviews(selectedProduct.getName());

        if (reviews != null) {
            uiManager.showMessage("\nThese are the reviews for \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() + "\" at \"" + selectedShopName + "\":\n");

            for (Review review : reviews) {
                uiManager.showMessage("\t" + review.getStars() + "* " + review.getComment());
            }

            double averageRating = productManager.calculateAverageRating(reviews);
            uiManager.showMessage("\n\tAverage rating: " + String.format("%.2f", averageRating) + "*");
        } else {
            uiManager.showMessage("Error: Unable to retrieve reviews for \"" + selectedProduct.getName() + "\" at \"" + selectedShopName + "\".");
        }

        handleShopSelection(selectedShopName);
    }

    private void reviewProductForShop(ProductDTO selectedProduct, String selectedShopName) {
        short stars = convertStarsToNumber(uiManager.askForString("\nPlease rate the product (1-5 stars): "));

        String comment = uiManager.askForString("Please add a comment to your review: ");

        productManager.addReview(selectedProduct.getName(), stars, comment);

        uiManager.showMessage("\nThank you for your review of \"" + selectedProduct.getName() +
                "\" by \"" + selectedProduct.getBrand() + "\" at \"" + selectedShopName + "\".");

        handleShopSelection(selectedShopName);
    }

    private void addToCart(ProductDTO selectedProduct, String selectedShopName) {
        cart.addItem(selectedProduct);
        uiManager.showMessage("\n1x \"" + selectedProduct.getName() + "\" by \"" + selectedProduct.getBrand() +
                "\" has been added to your cart.");
        handleShopSelection(selectedShopName);
    }

    private void cartManagement() {
    }

}