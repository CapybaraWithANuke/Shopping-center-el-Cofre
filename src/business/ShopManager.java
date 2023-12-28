package business;

import org.json.simple.parser.ParseException;
import persistence.BusinessModel;
import persistence.Shop;
import persistence.ProductCategory;
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

    public boolean isNameUnique(String name) {

        ArrayList<String> shops = null;

        try {
            shops = shopDAO.getAllShopNames();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        assert shops != null;
        for (String shop : shops) {
            if (shop.equals(name)) return false;
        }

        return true;

    }

    public boolean createShop(String name, String description, short since, String model) {

        Shop product = new Shop(name, description, since, 0.0,  (BusinessModel.valueOf(model)), new ArrayList<>());

        try {
            shopDAO.add(product);
            return true;
        } catch (IOException | ParseException ioException) {
            return false;
        }

    }


}
