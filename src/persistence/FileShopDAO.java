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

    @Override
    public void tryOpeningFile() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        parser.parse(new FileReader(filePath));

    }

    @Override
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

    @Override
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

    @Override
    public Shop getShop(String shop_name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        ArrayList<Shop> shops = new ArrayList<>();

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");

            if (name.equals(shop_name)) {

                String description = (String) jsonObject.get("description");
                short since = ((Long) jsonObject.get("since")).shortValue();
                double earnings = (double) jsonObject.get("earnings");
                BusinessModel businessModel = BusinessModel.valueOf((String) jsonObject.get("businessModel"));

                JSONArray arrayProducts = (JSONArray) jsonObject.get("catalogue");
                ArrayList<ProductInShop> catalogue = new ArrayList<>();

                for (Object product : arrayProducts) {

                    JSONObject productInShop = (JSONObject) product;

                    String product_name = (String) productInShop.get("name");
                    double price = (double) productInShop.get("price");

                    catalogue.add(new ProductInShop(product_name, price));

                }

                return new Shop(name, description, since, earnings, businessModel, catalogue);

            }

        }

        return null;
    }

    public void expandCatalogue(String shop_name, String product_name, double price) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        ArrayList<Shop> shops = new ArrayList<>();
        JSONArray newShops = new JSONArray();

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");

            String description = (String) jsonObject.get("description");
            short since = ((Long) jsonObject.get("since")).shortValue();
            double earnings = (double) jsonObject.get("earnings");
            BusinessModel businessModel = BusinessModel.valueOf((String) jsonObject.get("businessModel"));

            JSONArray arrayProducts = (JSONArray) jsonObject.get("catalogue");
            ArrayList<ProductInShop> catalogue = new ArrayList<>();

            for (Object product : arrayProducts) {

                JSONObject productInShop = (JSONObject) product;

                String product_name_from_file = (String) productInShop.get("name");
                double price_from_file = (double) productInShop.get("price");

                catalogue.add(new ProductInShop(product_name_from_file, price_from_file));


            }

            if (name.equals(shop_name)) {
                catalogue.add(new ProductInShop(product_name, price));
            }

            JSONObject newShop = new JSONObject();
            newShop.put("name", name);
            newShop.put("description", description);
            newShop.put("since", since);
            newShop.put("earnings", earnings);
            newShop.put("businessModel", businessModel.toString());
            JSONArray catalogue_array = new JSONArray();
            for (ProductInShop productInShop : catalogue) {
                JSONObject jsonObjectForProductInShop = new JSONObject();
                jsonObjectForProductInShop.put("name", productInShop.getName());
                jsonObjectForProductInShop.put("price", productInShop.getPrice());
                catalogue_array.add(jsonObjectForProductInShop);
            }
            newShop.put("catalogue", catalogue_array);
            newShops.add(newShop);

        }

        String stringedNewShops = newShops.toJSONString();
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(stringedNewShops);
        fileWriter.close();


    }

}
