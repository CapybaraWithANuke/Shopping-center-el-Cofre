package persistence;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface ProductDAO {
    ArrayList<Product> getAllProducts() throws IOException, ParseException;
    void add(Product product) throws IOException, ParseException;
}
