package business;

import org.json.simple.parser.ParseException;
import persistence.Product;
import persistence.ProductCategory;
import persistence.ProductDAO;
import persistence.Review;

import java.io.IOException;
import java.util.ArrayList;

public class ProductManager {

    private final ProductDAO productDAO;

    public ProductManager(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public boolean ifNameInSystem(String name) {

        ArrayList<String> products = null;

        try {
            products = productDAO.getAllProductNames();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        assert products != null;
        for (String product : products) {
            if (product.equals(name)) return true;
        }

        return false;

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

    public ArrayList<ProductDTO> getProductDTOs() {

        ArrayList<Product> products = null;
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();

        try {
            products = productDAO.getAllProducts();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        assert products != null;
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO(product.getName(), product.getBrand());
            productDTOs.add(productDTO);
        }

        return productDTOs;

    }

    public boolean deleteProduct(String name) {

        ArrayList<String> products = null;

        try {
            products = productDAO.getAllProductNames();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        for (int i = 0; i < products.size(); ++i) {

            if (products.get(i).equals(name)) {

                try {
                    productDAO.remove(i);
                } catch (IOException | ParseException exception) {
                    return false;
                }

            }

        }

        return true;

    }

    public boolean isPriceLessThanMRP(String product_name, Double price) {

        try {
            Product product = productDAO.getProduct(product_name);
            if (price <= product.getMrp())
                return true;

        } catch (IOException | ParseException exception) {
            return false;
        }

        return false;

    }

    public String getBrand(String product_name) {

        try {
            return productDAO.getBrandByName(product_name);
        } catch (IOException | ParseException exception) {
            return null;
        }

    }

    public ArrayList<String> getProductNames() {

        try {
            return productDAO.getAllProductNames();
        } catch (IOException | ParseException exception) {
            return null;
        }

    }

    public ProductDTO getProductDTO(String product_name) {
        try {
            Product product = productDAO.getProduct(product_name);
            return new ProductDTO(product.getName(), product.getBrand());
        } catch (IOException | ParseException exception) {
            return null;
        }
    }

    public ArrayList<ProductDTO> searchProducts(String query) {
        ArrayList<Product> products = null;
        ArrayList<ProductDTO> searchResults = new ArrayList<>();

        try {
            products = productDAO.getAllProducts();
        } catch (IOException | ParseException exception) {
            exception.printStackTrace();
        }

        if (products != null) {
            for (Product product : products) {
                // Perform case-insensitive search based on product name or brand
                if (product.getName().toLowerCase().contains(query.toLowerCase()) ||
                        product.getBrand().toLowerCase().contains(query.toLowerCase())) {
                    ProductDTO productDTO = new ProductDTO(product.getName(), product.getBrand());
                    searchResults.add(productDTO);
                }
            }
        }

        return searchResults;
    }

    public void addReview(String productName, short stars, String comment) {
        try {
            ArrayList<Product> products = productDAO.getAllProducts();

            for (Product product : products) {
                if (product.getName().equals(productName)) {
                    product.addReview(new Review(stars, comment));
                    productDAO.update(product);
                    break;
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Review> getReviews(String productName) {
        try {
            Product product = productDAO.getProduct(productName);
            return product.getReviews();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public double calculateAverageRating(ArrayList<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        int totalStars = 0;
        for (Review review : reviews) {
            totalStars += review.getStars();
        }

        return (double) totalStars / reviews.size();
    }

}
