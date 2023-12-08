package business;

public class ProductDTO {

    private String name;
    private String brand;

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

}
