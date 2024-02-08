package business;

public class ShopDTO {
    private final String name;
    private final String description;
    private final short since;
    private final String businessModel;

    public ShopDTO(String name, String description, short since, String businessModel) {
        this.name = name;
        this.description = description;
        this.since = since;
        this.businessModel = businessModel;
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

    public String getBusinessModel() {
        return businessModel;
    }
}
