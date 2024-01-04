import business.ProductManager;
import business.ShopManager;
import persistence.FileProductDAO;
import persistence.FileShopDAO;
import persistence.ProductDAO;
import persistence.ShopDAO;
import presentation.Controller;
import presentation.UIManager;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        ShopDAO shopDAO = new FileShopDAO();
        ProductDAO productDAO = new FileProductDAO();

        ProductManager productManager = new ProductManager(productDAO);
        ShopManager shopManager = new ShopManager(shopDAO, productManager);
        UIManager uiManager = new UIManager();
        Controller controller = new Controller(uiManager, productManager, shopManager);

        controller.run();
    }
}