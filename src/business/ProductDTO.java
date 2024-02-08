package business;

import persistence.Review;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private String name;
    private String brand;
    private ArrayList<Review> reviews;  // Add this line

    public ProductDTO(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    // Add a method to set the reviews if needed
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
