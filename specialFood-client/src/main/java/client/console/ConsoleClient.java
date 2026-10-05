package client.console;

import java.util.List;
import client.data.Credentials;
import client.data.Product;
import client.proxies.HttpServiceProxy;
import client.proxies.ISFServiceProxy;

public class ConsoleClient {

    // Simple manual instantiation
    private static final ISFServiceProxy serviceProxy = new HttpServiceProxy();

    public static void main(String[] args) {
        System.out.println("--- Starting Special Foods Console Client (Manual HTTP) ---");
        
        String token = null;

        try {
            // 1. LOGIN
            System.out.println("Attempting login for user: A");
            token = serviceProxy.login(new Credentials("A", "B"));
            
            if (token != null) {
                System.out.println("Login successful. Token: " + token);

                // 2. SEARCH
                String searchName = "Mango";
                System.out.println("Searching for: " + searchName);
                List<Product> products = serviceProxy.searchProductsByName(searchName);

                if (products != null && !products.isEmpty()) {
                    Product p = products.get(0);
                    System.out.println("Found product: " + p.name() + " (ID: " + p.id() + ")");

                    // 3. PURCHASE
                    System.out.println("Buying 5 units...");
                    serviceProxy.buyProducts(token, (int)p.id(), 5);
                    System.out.println("Purchase successful!");
                }

                // 4. LOGOUT
                serviceProxy.logout(token);
                System.out.println("Logout complete. Session closed.");
            }
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}