package presentation;

import business.ProductManager;
import business.ShopManager;

public class Controller {

    private static UIManager uiManager;
    private static ProductManager productManager;
    private static ShopManager shopManager;

    Controller(UIManager uiManager, ProductManager productManager, ShopManager shopManager) {
        Controller.uiManager = uiManager;
        Controller.productManager = productManager;
        Controller.shopManager = shopManager;
    }

}
