package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileShopDAO implements ShopDAO{

    private static final String filePath = "src/files/shops.json";

    public void tryOpeningFile() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        parser.parse(new FileReader(filePath));

    }

}
