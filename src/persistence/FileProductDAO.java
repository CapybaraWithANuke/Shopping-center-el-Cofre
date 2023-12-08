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

    public void remove(int index) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

        jsonArray.remove(index);

        String stringedJsonArray = jsonArray.toJSONString();
        FileWriter fileWriter = new FileWriter(filePath);

        fileWriter.write(stringedJsonArray);
        fileWriter.close();

    }

}
