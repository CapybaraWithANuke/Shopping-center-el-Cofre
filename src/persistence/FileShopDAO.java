package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
    public ArrayList<Shop> getAllShops() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        ArrayList<Shop> shops = new ArrayList<>();

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

                String product_name = (String) productInShop.get("name");
                double price = (double) productInShop.get("price");

                catalogue.add(new ProductInShop(product_name, price));

            }

            shops.add(new Shop(name, description, since, earnings, businessModel, catalogue));

        }

        return shops;
    }

    @Override
    public void removeAll() throws IOException {
        FileWriter filewriter = new FileWriter(filePath);
        filewriter.write("[]");
        filewriter.close();
    }

    @Override
    public void add(Shop shop) throws IOException, ParseException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", shop.name());
        jsonObject.put("description", shop.description());
        jsonObject.put("since", shop.since());
        jsonObject.put("earnings", shop.earnings());
        jsonObject.put("businessModel", shop.model().toString());
        JSONArray catalogue_array = new JSONArray();
        for (ProductInShop productInShop : shop.getCatalogue()) {
            JSONObject jsonObjectForProductInShop = new JSONObject();
            jsonObjectForProductInShop.put("name", productInShop.getName());
            jsonObjectForProductInShop.put("price", productInShop.getPrice());
            catalogue_array.add(jsonObjectForProductInShop);
        }
        jsonObject.put("catalogue", catalogue_array);


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


    @Override
    public ArrayList<String> getAllShopsProducts(String shop_name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");

            if (name.equals(shop_name)) {

                JSONArray arrayProducts = (JSONArray) jsonObject.get("catalogue");

                ArrayList<String> products = new ArrayList<>();

                for (Object catalogue_entry : arrayProducts) {

                    JSONObject product = (JSONObject) catalogue_entry;

                    products.add((String) product.get("name"));

                }

                return products;
            }
        }

        return null;
    }



}
