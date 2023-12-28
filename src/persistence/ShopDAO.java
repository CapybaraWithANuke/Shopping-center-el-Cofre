package persistence;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ShopDAO {
    void tryOpeningFile() throws IOException, ParseException;

    ArrayList<String> getAllShopNames() throws IOException, ParseException;
    void add(Shop shop) throws IOException, ParseException;
}
