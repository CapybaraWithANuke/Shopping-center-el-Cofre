package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileShopDAO implements ShopDAO{

    private static final String filePath = "src/files/shops.json";

    public void tryOpeningFile() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        parser.parse(new FileReader(filePath));

    }

    public ArrayList<String> getAllShopNames() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        ArrayList<String> names = new ArrayList<>();

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");
            names.add(name);

        }

        return names;

    }

    public void add(Shop shop) throws IOException, ParseException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", shop.getName());
        jsonObject.put("description", shop.getDescription());
        jsonObject.put("since", shop.getSince());
        jsonObject.put("earnings", shop.getEarnings());
        jsonObject.put("businessModel", shop.getModel().toString());
        jsonObject.put("catalogue", shop.getCatalogue());


        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        jsonArray.add(jsonObject);
        String stringedJsonArray = jsonArray.toJSONString();

        FileWriter fileWriter = new FileWriter(filePath);

        fileWriter.write(stringedJsonArray);
        fileWriter.close();
    }

}
