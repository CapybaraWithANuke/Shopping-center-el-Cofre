package persistence;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ShopDAO {
    void tryOpeningFile() throws IOException, ParseException;
    ArrayList<String> getAllShopNames() throws IOException, ParseException;
    void add(Shop shop) throws IOException, ParseException;
    Shop getShop(String shop_name) throws IOException, ParseException;
    void expandCatalogue(String shop_name, String product_name, double price) throws IOException, ParseException;
    ArrayList<String> getAllShopsProducts(String shop_name) throws IOException, ParseException;
    ArrayList<Shop> getAllShops() throws IOException, ParseException;
    void removeAll() throws IOException;
}
