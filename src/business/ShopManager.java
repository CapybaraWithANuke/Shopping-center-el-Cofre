package business;

import org.json.simple.parser.ParseException;
import persistence.BusinessModel;
import persistence.ProductInShop;
import persistence.Shop;
import persistence.ShopDAO;

import java.io.IOException;
import java.util.ArrayList;


public class ShopManager {

    private final ShopDAO shopDAO;
    private final ProductManager productManager;

    public ShopManager(ShopDAO shopDAO, ProductManager productManager) {
        this.shopDAO = shopDAO;
        this.productManager = productManager;
    }

    public boolean ifFileExists() {
        try {
            shopDAO.tryOpeningFile();
            return true;
        } catch (IOException | ParseException exception) {
            return false;
        }
    }

    public boolean ifNameInSystem(String name) {

        ArrayList<String> shops = null;

        try {
            shops = shopDAO.getAllShopNames();
        } catch (IOException | ParseException exception) {
            return false;
        }

        assert shops != null;
        for (String shop : shops) {
            if (shop.equals(name)) return true;
        }

        return false;

    }

    public boolean createShop(String name, String description, short since, String model) {

        Shop product = new Shop(name, description, since, 0.0,  (BusinessModel.valueOf(model)), new ArrayList<>());

        try {
            shopDAO.add(product);
            return true;
        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public boolean isProductInShop(String shop_name, String product_name) {

        try {
            Shop shop = shopDAO.getShop(shop_name);
            ArrayList<ProductInShop> catalogue = shop.getCatalogue();
            for (ProductInShop productInShop : catalogue) {
                if (productInShop.getName().equals(product_name))
                    return true;
            }

            return false;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public boolean addProductToCatalogue(String shop_name, String product_name, double price) {

        try {

            ArrayList<Shop> shops = shopDAO.getAllShops();
            shopDAO.removeAll();

            for (Shop shop : shops) {

                if (shop.getName().equals(shop_name)) {
                    shop.getCatalogue().add(new ProductInShop(product_name, price));
                }

                shopDAO.add(shop);

            }
            return true;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public ArrayList<ProductDTO> getProductDTOs(String shop_name) {

        ArrayList<String> product_names;
        try {
            product_names = shopDAO.getAllShopsProducts(shop_name);
        } catch (IOException | ParseException exception) {
            return null;
        }

        ArrayList<ProductDTO> productDTOs = new ArrayList<>();

        assert product_names != null;
        for (String product_name : product_names) {
            ProductDTO productDTO = productManager.getProductDTO(product_name);
            productDTOs.add(productDTO);
        }

        return productDTOs;

    }

    public ArrayList<ProductDTO> getProductDTOsShop(String shop_name) {
        ArrayList<ProductInShop> productsInShop;
        try {
            productsInShop = shopDAO.getShop(shop_name).getCatalogue();
        } catch (IOException | ParseException exception) {
            return null;
        }

        ArrayList<ProductDTO> productDTOs = new ArrayList<>();

        for (ProductInShop productInShop : productsInShop) {
            ProductDTO productDTO = productManager.getProductDTO(productInShop.getName());
            productDTOs.add(productDTO);
        }

        return productDTOs;
    }

    public boolean deleteProduct(String shop_name, int productID) {

         try {

            ArrayList<Shop> shops = shopDAO.getAllShops();
            shopDAO.removeAll();

            for (Shop shop : shops) {

                if (shop.getName().equals(shop_name)) {
                    shop.getCatalogue().remove(productID);
                }

                shopDAO.add(shop);

            }
            return true;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public ArrayList<String> getShopsForProduct(String productName) {
        ArrayList<String> shopsInfo = new ArrayList<>();

        try {
            ArrayList<Shop> shops = shopDAO.getAllShops();
            for (Shop shop : shops) {
                if (isProductInShop(shop.getName(), productName)) {
                    double price = getProductPrice(shop.getName(), productName);
                    shopsInfo.add(shop.getName() + ": " + price);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return shopsInfo;
    }

    public double getProductPrice(String shopName, String productName) {
        try {
            Shop shop = shopDAO.getShop(shopName);
            ArrayList<ProductInShop> catalogue = shop.getCatalogue();
            for (ProductInShop productInShop : catalogue) {
                if (productInShop.getName().equals(productName)) {
                    return productInShop.getPrice();
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return 0.0; // Default price if not found
    }


    //////////


    public ArrayList<String> getAllShopNames() {
        try {
            return shopDAO.getAllShopNames();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public ShopDTO getShopDetails(String shopName) {
        try {
            Shop shop = shopDAO.getShop(shopName);
            if (shop != null) {
                return new ShopDTO(shop.getName(), shop.getDescription(), shop.getSince(), shop.getBusinessModel().toString());
            } else {
                return null; // Indicate that the shop was not found
            }
        } catch (IOException | ParseException e) {
            return null; // Indicate that an error occurred
        }
    }


}
