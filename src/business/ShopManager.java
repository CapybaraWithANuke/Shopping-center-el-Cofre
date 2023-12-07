package business;

import org.json.simple.parser.ParseException;
import persistence.ShopDAO;

import java.io.IOException;

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


}
