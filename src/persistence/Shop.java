package persistence;

import java.util.ArrayList;

public class Shop {

    private String name;
    private String description;
    private short since;
    private double earnings;
    private ArrayList<String> catalogue;
    private BusinessModel model;

    public Shop(String name, String description, short since, double earnings, BusinessModel model, ArrayList<String> catalogue) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.earnings = earnings;
        this.model = model;
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

    public ArrayList<String> getCatalogue() {
        return catalogue;
    }

    public BusinessModel getModel() {
        return model;
    }
}
