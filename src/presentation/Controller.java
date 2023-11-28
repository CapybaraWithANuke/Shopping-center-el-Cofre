package presentation;

import business.ProductManager;
import business.ShopManager;

public class Controller {

    private static UIManager uiManager;
    private static ProductManager productManager;
    private static ShopManager shopManager;

    public Controller(UIManager uiManager, ProductManager productManager, ShopManager shopManager) {
        Controller.uiManager = uiManager;
        Controller.productManager = productManager;
        Controller.shopManager = shopManager;
    }

    public void run() {

        uiManager.showMessage("        ________      ____\n" +
                            "  ___  / / ____/___  / __/_______\n" +
                           " / _ \\/ / / " + "  / __ \\/ /_/ ___/ _ \\\n" +
                         "/  __/ / /___/ /_/ / __/ /\t/  __/\n" +
                         "\\___/_/\\____/\\____/_/ /_/\t" + "\\___/");
        short name = uiManager.askForShort("Could you tell me your name pwease >_<:");
        System.out.println(name);


    }

}
