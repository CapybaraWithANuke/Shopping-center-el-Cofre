package presentation;
import business.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ProductDTO> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(ProductDTO product) {
        items.add(product);
    }

    public void removeItem(ProductDTO product) {
        items.remove(product);
    }

    public List<ProductDTO> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
