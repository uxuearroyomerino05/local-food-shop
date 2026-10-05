/*
 *  The methods have been created by Copilot and updated by the user.
 */


package specialFoods.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import specialFoods.dao.ProductRepository;
import specialFoods.dto.ProductDTO;
import specialFoods.entity.Product;
import specialFoods.entity.Purchase;
import specialFoods.entity.User;
import specialFoods.external.ProductSellerFactory;

@Service
public class SpecialFoodService {
	
	private static final Logger logger = LoggerFactory.getLogger(SpecialFoodService.class);
	
	
	// Product Seller Factory
	private ProductSellerFactory productSellerFactory;
	
	// List of external companies
	private static final List<String> EXTERNAL_COMPANIES = Arrays.asList("APP", "FARCOOP");
	
	private final ProductRepository productRepository;

    public SpecialFoodService(ProductRepository productRepository, ProductSellerFactory productSellerFactory) {
		this.productSellerFactory = productSellerFactory;
    	this.productRepository = productRepository;
    }
	
// --- Methods for External Repository ---
    
    
    private Product convertToEntity(ProductDTO dto) {
        return new Product(
            dto.getName(),
            dto.getDescription(),
            dto.getProducerName(),
            dto.getProducerOrigin(),
            dto.getPrice(),
            dto.getAvailableQuantity(),
            dto.getCompany()
        );
    }
    
    // Update existing product with new data from DTO
    private void updateExistingProduct(Product existingProduct, ProductDTO dto) {
        existingProduct.setDescription(dto.getDescription());
        existingProduct.setProducerName(dto.getProducerName());
        existingProduct.setProducerOrigin(dto.getProducerOrigin());
        existingProduct.setPrice(dto.getPrice());
        existingProduct.setAvailableQuantity(dto.getAvailableQuantity());
    }
    
    // Refresh all products from external companies
    public void refreshAllProducts() {
        logger.info("Starting refresh of all external products...");

        for (String company : EXTERNAL_COMPANIES) {
            try {
            	logger.info("Fetching products from company: {}", company);
                List<ProductDTO> dtoList = productSellerFactory.createGateway(company).getProducts();
                logger.info("Downloading the products of : {} company", company);

                List<Product> productsToSave = new ArrayList<>();

                for (ProductDTO dto : dtoList) {
                    // Find existing product by name and company
                    Optional<Product> existingProduct = productRepository.findByNameAndCompany(dto.getName(), dto.getCompany());
                    
                    Product product;
                    
                    if (existingProduct.isPresent()) {
                        //Update existing product
                        product = existingProduct.get();
                        updateExistingProduct(product, dto);
                    } else {
                    	//Create new product
                        product = convertToEntity(dto);
                    }
                    
                    productsToSave.add(product);
                }

                // Save all updated/new products in repository
                productRepository.saveAll(productsToSave);
                logger.info("Successfully refreshed {} products from {}", productsToSave.size(), company);
            } catch (Exception e) {
                logger.error("Error refreshing products from {}: {}", company, e.getMessage());
            }
        }
    }


    // --- Methods for Internal Repository ---
	
	
	// Get all products from a Company
	public List<Product> getAllProductsFromCompany(String company) {
		return new ArrayList<>(productRepository.findByCompanyContainingIgnoreCase(company));
	}	
	
	// Get products by Name
	public List<Product> getProductsByName(String name) {
		return productRepository.findByNameContainingIgnoreCase(name);
		
	}
	
	// Get products by Description
	public List<Product> getProductsByDescription(String description) {
		return productRepository.findByDescriptionContainingIgnoreCase(description);
	}
	
	// Get products by ProducerName
	public List<Product> getProductsByProducerName(String producerName) {
		return productRepository.findByProducerNameContainingIgnoreCase(producerName);
	}
	
	// Get products by ProducerOrigin
	public List<Product> getProductsByProducerOrigin(String producerOrigin) {
		return productRepository.findByProducerOriginContainingIgnoreCase(producerOrigin);
	}
	
	//Get product by Id
	public Optional<Product> getProductById(long id) {
		return productRepository.findById(id);
	}
	
	// Get all products
	public List<Product> getAllProducts() {
		return new ArrayList<>(productRepository.findAll());
	}
	
	//Get products by name from company
	public List<Product> getProductsByNameFromCompany(String company, String name) {
		return productRepository.findByCompanyAndNameContainingIgnoreCase(company, name);
	}
	
	//Get products by description from company
	public List<Product> getProductsByDescriptionFromCompany(String company, String description) {
		return productRepository.findByCompanyAndDescriptionContainingIgnoreCase(company, description);
	}
	
	//Get products by producer name from company
	public List<Product> getProductsByProducerNameFromCompany(String company, String producerName) {
		return productRepository.findByCompanyAndProducerNameContainingIgnoreCase(company, producerName);
	}
	
	//Get products by producer origin from company
	public List<Product> getProductsByProducerOriginFromCompany(String company, String producerOrigin) {
		return productRepository.findByCompanyAndProducerOriginContainingIgnoreCase(company, producerOrigin);
	}
	
	
	// Buy product (create a purchase)
	public void buyProduct(User user, long productId, int quantity) {
		
		logger.info("User {} is trying to buy {} units of product with id {}", user.getUsername(), quantity, productId);
		
		// Find product by id
		Optional<Product> product = productRepository.findById(productId);
		
		// Validate product and quantity
		if (product.isEmpty()) { 
	        throw new RuntimeException("Product not found");
	    }
		logger.info("Product found: {}", product.get().getName());
		
	    if (quantity <= 0) {
	        throw new RuntimeException("Quantity must be greater than zero");
	    }
	    logger.info("Quantity requested: {}", quantity);

		if (product.get().getAvailableQuantity() < quantity) {
			throw new RuntimeException("Quantity available must be greater or equal than quantity asked");
		}
		logger.info("Sufficient stock available: {}", product.get().getAvailableQuantity());
		
		// Create a new purchase and associate it with the product
	    Purchase purchase = new Purchase(System.currentTimeMillis(), quantity, product.get(), user);
	    logger.info("Purchase created for user {}: {} units of product {}", user.getUsername(), quantity, product.get().getName());
	    
	    // Add purchase to user and product
	    product.get().addPurchase(purchase);
	    logger.info("Purchase associated with product {}", user.getUsername(), product.get().getName());
	    
	    // Decrement stock
	    product.get().setAvailableQuantity(product.get().getAvailableQuantity() - quantity);
		logger.info("Stock updated for product {}: new available quantity is {}", product.get().getName(), product.get().getAvailableQuantity());
	    
		// Save updated product and user with the new purchase
        productRepository.save(product.get());
        logger.info("Product updated in the repository");
	}
	
	
	
}