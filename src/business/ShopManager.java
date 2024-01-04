package business;

import org.json.simple.parser.ParseException;
import persistence.BusinessModel;
import persistence.ProductInShop;
import persistence.Shop;
import persistence.ShopDAO;

import java.io.IOException;
import java.util.ArrayList;


public class ShopManager {

    private final ShopDAO shopDAO;
    private final ProductManager productManager;

    public ShopManager(ShopDAO shopDAO, ProductManager productManager) {
        this.shopDAO = shopDAO;
        this.productManager = productManager;
    }

    public boolean ifFileExists() {
        try {
            shopDAO.tryOpeningFile();
            return true;
        } catch (IOException | ParseException exception) {
            return false;
        }
    }

    public boolean ifNameInSystem(String name) {

        ArrayList<String> shops = null;

        try {
            shops = shopDAO.getAllShopNames();
        } catch (IOException | ParseException exception) {
            return false;
        }

        assert shops != null;
        for (String shop : shops) {
            if (shop.equals(name)) return true;
        }

        return false;

    }

    public boolean createShop(String name, String description, short since, String model) {

        Shop product = new Shop(name, description, since, 0.0,  (BusinessModel.valueOf(model)), new ArrayList<>());

        try {
            shopDAO.add(product);
            return true;
        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public boolean isProductInShop(String shop_name, String product_name) {

        try {
            Shop shop = shopDAO.getShop(shop_name);
            ArrayList<ProductInShop> catalogue = shop.catalogue();
            for (ProductInShop productInShop : catalogue) {
                if (productInShop.getName().equals(product_name))
                    return true;
            }

            return false;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public boolean addProductToCatalogue(String shop_name, String product_name, double price) {

        try {

            ArrayList<Shop> shops = shopDAO.getAllShops();
            shopDAO.removeAll();

            for (Shop shop : shops) {

                if (shop.getName().equals(shop_name)) {
                    shop.getCatalogue().add(new ProductInShop(product_name, price));
                }

                shopDAO.add(shop);

            }
            return true;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }

    public ArrayList<ProductDTO> getProductDTOs(String shop_name) {

        ArrayList<String> product_names;
        try {
            product_names = shopDAO.getAllShopsProducts(shop_name);
        } catch (IOException | ParseException exception) {
            return null;
        }

        ArrayList<ProductDTO> productDTOs = new ArrayList<>();

        assert product_names != null;
        for (String product_name : product_names) {
            ProductDTO productDTO = productManager.getProductDTO(product_name);
            productDTOs.add(productDTO);
        }

        return productDTOs;

    }

    public boolean deleteProduct(String shop_name, int productID) {

         try {

            ArrayList<Shop> shops = shopDAO.getAllShops();
            shopDAO.removeAll();

            for (Shop shop : shops) {

                if (shop.getName().equals(shop_name)) {
                    shop.getCatalogue().remove(productID);
                }

                shopDAO.add(shop);

            }
            return true;

        } catch (IOException | ParseException exception) {
            return false;
        }

    }


}
