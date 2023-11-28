import business.ProductManager;
import business.ShopManager;
import presentation.Controller;
import presentation.UIManager;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        ProductManager productManager = new ProductManager();
        ShopManager shopManager = new ShopManager();
        UIManager uiManager = new UIManager();
        Controller controller = new Controller(uiManager, productManager, shopManager);

        controller.run();
    }
}