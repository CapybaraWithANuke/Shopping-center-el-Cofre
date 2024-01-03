package persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProductDAO implements ProductDAO {

    private static final String filePath = "src/files/products.json";

    @Override
    public ArrayList<Product> getAllProducts() throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
        ArrayList<Product> products = new ArrayList<>();

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");
            String brand = (String) jsonObject.get("brand");
            double mrp =  (double) jsonObject.get("mrp");
            ProductCategory productCategory = ProductCategory.valueOf((String) jsonObject.get("category"));

            JSONArray arrayReviews = (JSONArray) jsonObject.get("reviews");
            ArrayList<String> reviews = new ArrayList<>();

            for (Object review : arrayReviews) {
                reviews.add((String) review);
            }

            Product product = new Product(name, brand, mrp, productCategory, reviews);

            products.add(product);

        }

        return products;

    }

    @Override
    public ArrayList<String> getAllProductNames() throws IOException, ParseException {

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
    public void add(Product product) throws IOException, ParseException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", product.getName());
        jsonObject.put("brand", product.getBrand());
        jsonObject.put("mrp", product.getMrp());
        jsonObject.put("category", product.getCategory().toString());
        jsonObject.put("reviews", product.getReviews());

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        jsonArray.add(jsonObject);
        String stringedJsonArray = jsonArray.toJSONString();

        FileWriter fileWriter = new FileWriter(filePath);

        fileWriter.write(stringedJsonArray);
        fileWriter.close();

    }

    @Override
    public void remove(int index) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        jsonArray.remove(index);

        String stringedJsonArray = jsonArray.toJSONString();
        FileWriter fileWriter = new FileWriter(filePath);

        fileWriter.write(stringedJsonArray);
        fileWriter.close();

    }

    @Override
    public Product getProduct(String product_name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");

            if (name.equals(product_name)) {

                String brand = (String) jsonObject.get("brand");
                double mrp =  (double) jsonObject.get("mrp");
                ProductCategory productCategory = ProductCategory.valueOf((String) jsonObject.get("category"));

                JSONArray arrayReviews = (JSONArray) jsonObject.get("reviews");
                ArrayList<String> reviews = new ArrayList<>();

                for (Object review : arrayReviews) {
                    reviews.add((String) review);
                }

                return new Product(name, brand, mrp, productCategory, reviews);

            }

        }

        return null;

    }

    @Override
    public String getBrandByName(String product_name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        for (Object object : jsonArray) {

            JSONObject jsonObject = (JSONObject) object;

            String name = (String) jsonObject.get("name");

            if (name.equals(product_name)) {
                String brand = (String) jsonObject.get("brand");
                return brand;
            }

        }

        return null;
    }
}
