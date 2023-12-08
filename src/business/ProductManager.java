package business;

import org.json.simple.parser.ParseException;
import persistence.Product;
import persistence.ProductCategory;
import persistence.ProductDAO;

import java.io.IOException;
import java.util.ArrayList;

public class ProductManager {

    private static ProductDAO productDAO;

    public ProductManager(ProductDAO productDAO) {
        ProductManager.productDAO = productDAO;
    }

    public boolean checkIfUnique(String name) {

        ArrayList<Product> products = null;

        try {
            products = productDAO.getAllProducts();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        assert products != null;
        for (Product product : products) {
            if (product.getName().equals(name)) return false;
        }

        return true;

    }

    public boolean createProduct(String name, String brand, double mrp, String category) {

        Product product = new Product(name, brand, mrp, (ProductCategory.valueOf(category)), new ArrayList<>());

        try {
            productDAO.add(product);
            return true;
        } catch (IOException | ParseException ioException) {
            return false;
        }
    }

}
