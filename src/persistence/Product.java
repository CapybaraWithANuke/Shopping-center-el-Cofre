package persistence;

import java.util.ArrayList;

public class Product {
    private String name;
    private String brand;
    private double mrp;
    private ArrayList<Review> reviews;
    private double review_score;
    private ProductCategory category;

    public Product(String name, String brand, double mrp, ProductCategory category, ArrayList<Review> reviews) {
        this.name = name;
        this.brand = brand;
        this.mrp = mrp;
        this.category = category;
        this.reviews = reviews;
        this.review_score = 0;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getMrp() {
        return mrp;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ProductCategory getCategory() {
        return category;
    }
}
