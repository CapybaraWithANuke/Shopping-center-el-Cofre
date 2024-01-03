package business;

import org.json.simple.parser.ParseException;
import persistence.BusinessModel;
import persistence.ProductInShop;
import persistence.Shop;
import persistence.ShopDAO;

import java.io.IOException;
import java.util.ArrayList;

public class ShopManager {

    private static ShopDAO shopDAO;

    public ShopManager(ShopDAO shopDAO) {
        ShopManager.shopDAO = shopDAO;
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
            exception.printStackTrace();
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
            shopDAO.expandCatalogue(shop_name, product_name, price);
            return true;
        } catch (IOException | ParseException exception) {
            return false;
        }

    }


}
