package persistence;

import java.util.ArrayList;

public class Shop{

    private final String name;
    private final String description;
    private final short since;
    private final double earnings;
    private final BusinessModel businessModel;
    private final ArrayList<ProductInShop> catalogue;

    public Shop(String name, String description, short since, double earnings, BusinessModel businessModel,
                ArrayList<ProductInShop> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.businessModel = businessModel;
        this.catalogue = catalogue;
    }

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

    public BusinessModel getBusinessModel() {
        return businessModel;
    }

    public ArrayList<ProductInShop> getCatalogue() {
        return catalogue;
    }
}
