package persistence;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ProductDAO {
    ArrayList<Product> getAllProducts() throws IOException, ParseException;
    ArrayList<String> getAllProductNames() throws IOException, ParseException;
    void add(Product product) throws IOException, ParseException;
    void remove(int i) throws IOException, ParseException;
    Product getProduct(String product_name) throws IOException, ParseException;
    String getBrandByName(String product_name) throws IOException, ParseException;
    void update(Product product) throws IOException, ParseException;  // Add this line
    void removeAll() throws IOException;
}
