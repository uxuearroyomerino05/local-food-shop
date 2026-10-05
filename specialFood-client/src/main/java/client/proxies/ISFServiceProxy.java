package client.proxies;

import java.util.List;
import java.util.Optional;

import client.data.Credentials;
import client.data.Product;

/**
 * Interface for the Special Foods Service Proxy
 * Defines all operations that can be performed against the SF server
 */
public interface ISFServiceProxy {
    
    /**
     * Authenticate user and obtain a token
     * @param credentials User credentials (username and password)
     * @return Authentication token
     */
    String login(Credentials credentials);
    
    /**
     * Logout and invalidate the token
     * @param token Authentication token
     */
    void logout(String token);
    
    /**
	 * Get all products from both companies (FarCoop and APP)
	 * @return List of all products
	 */
    List<Product> getAllProducts();
    
    /**
	 * Get product by its ID
	 * @param productId ID of the product
	 * @return Optional containing the product if found, empty otherwise
	 */
    Optional<Product> getProductById(long productId);
    
    /**
     * Get all products from a specific company (FarCoop or APP)
     * @param companyName Name of the company
     * @return List of products from the specified company
     */
    List<Product> getProductsByCompany(String companyName);
    
    /**
	 * Get products by name from a specific company
	 * @param companyName Name of the company
	 * @param productName Name of the product
	 * @return List of matching products from the specified company
	 */
    List<Product> getProductsByNamefromCompany(String companyName, String productName);
    
    /**
	 * Get products by description from a specific company
	 * @param companyName
	 * @param description
	 * @return
	 */
    List<Product> getProductsByDescriptionFromCompany(String companyName, String description);
    
    /**
     * Get products by producer from a specific company
     * @param companyName
     * @param producerName
     * @return
     */
    List<Product> getProductsByProducerFromCompany(String companyName, String producerName);
    
    
    /**
	 * Get products by origin from a specific company
	 * @param companyName
	 * @param producerOrigin
	 * @return
	 */
    List<Product> getProductsByOriginFromCompany(String companyName, String producerOrigin);
    
    /**
     * Search products by name
     * @param productName Name or partial name of the product
     * @return List of matching products
     */
    List<Product> searchProductsByName(String productName);
    
    /**
     * Search products by description
     * @param description Description or partial description
     * @return List of matching products
     */
    List<Product> searchProductsByDescription(String description);
    
    /**
     * Search products by producer/farm name
     * @param producerName Name of the producer or farm
     * @return List of matching products
     */
    List<Product> searchProductsByProducerName(String producerName);
    
    /**
     * Search products by producer origin/location
     * @param producerOrigin Origin or location of the producer
     * @return List of matching products
     */
    List<Product> searchProductsByProducerOrigin(String producerOrigin);
    
    /**
     * Purchase a product
     * @param token Authentication token
     * @param productId ID of the product to purchase
     * @param quantity Quantity to purchase
     */
    void buyProducts(String token, long productId, int quantity);
    
}
