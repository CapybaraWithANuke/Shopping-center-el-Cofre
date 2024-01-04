package persistence;

import java.util.ArrayList;

public record Shop(String name, String description, short since, double earnings, BusinessModel model,
                   ArrayList<ProductInShop> catalogue) {


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public short getSince() {
        return since;
    }

    public double getEarnings() {
        return earnings;
    }

    public BusinessModel getModel() {
        return model;
    }

    public ArrayList<ProductInShop> getCatalogue() {
        return catalogue;
    }
}
