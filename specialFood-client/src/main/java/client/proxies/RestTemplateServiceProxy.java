package client.proxies;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import client.data.Credentials;
import client.data.Product;

@Service
public class RestTemplateServiceProxy implements ISFServiceProxy{
	
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateServiceProxy.class);
	
	private final RestTemplate restTemplate;

	@Value("${api.base.url}")
    private String apiBaseUrl;  
    
    public RestTemplateServiceProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
	
	@Override
	public String login(Credentials credentials) {
		logger.info("Api base URL: {}", apiBaseUrl);
		String url = apiBaseUrl + "/auth/login";
        
        try {
            return restTemplate.postForObject(url, credentials, String.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Login failed: Invalid credentials.");
                default -> throw new RuntimeException("Login failed: " + e.getStatusText());
            }
        }
	}

	@Override
	public void logout(String token) {
		String url = apiBaseUrl + "/auth/logout";
        
        try {
            restTemplate.postForObject(url, token, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("Logout failed: Invalid token.");
                default -> throw new RuntimeException("Logout failed: " + e.getStatusText());
            }
        }
		
	}
	
	@Override
	public List<Product> getAllProducts() {
	    String url = apiBaseUrl + "/specialFood/products";

	    try {
	        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
	        
	        Product[] products = response.getBody();
	        
	        // LOG TEMPORAL PARA DEBUG
	        if (products != null && products.length > 0) {
	            logger.info("First product: " + products[0]);
	        }
	        
	        return products != null ? Arrays.asList(products) : List.of();
	    } catch (HttpStatusCodeException e) {
	        throw new RuntimeException("Failed to retrieve all products: " + e.getStatusText());
	    }
	}

	@Override
	public Optional<Product> getProductById(long productId) {
	    String url = apiBaseUrl + "/specialFood/products/id/" + productId;

	    try {
	        Product product = restTemplate.getForObject(url, Product.class);
	        return Optional.ofNullable(product);
	    } catch (HttpStatusCodeException e) {
	        if (e.getStatusCode().value() == 404) {
	            return Optional.empty();
	        }
	        throw new RuntimeException("Failed to retrieve product: " + e.getStatusText());
	    }
	}
	
	@Override
	public List<Product> getProductsByCompany(String companyName) {
		String url = apiBaseUrl + "/specialFood/products/" + companyName;
        
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("Company not found: " + companyName);
                default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
            }
        }
	}
	
	@Override
	public List<Product> getProductsByNamefromCompany(String companyName, String productName) {
	    String url = apiBaseUrl + "/specialFood/products/" + companyName
	               + "/byName?name=" + productName;

	    try {
	        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
	        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
	    } catch (HttpStatusCodeException e) {
	        switch (e.getStatusCode().value()) {
	            case 404 -> throw new RuntimeException("Products not found for company " + companyName);
	            default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
	        }
	    }
	}


	@Override
	public List<Product> getProductsByDescriptionFromCompany(String companyName, String description) {
	    String url = apiBaseUrl + "/specialFood/products/" + companyName
	               + "/byDescription?description=" + description;

	    try {
	        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
	        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
	    } catch (HttpStatusCodeException e) {
	        switch (e.getStatusCode().value()) {
	            case 404 -> throw new RuntimeException("Products not found for company " + companyName);
	            default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
	        }
	    }
	}


	@Override
	public List<Product> getProductsByProducerFromCompany(String companyName, String producerName) {
	    String url = apiBaseUrl + "/specialFood/products/" + companyName
	               + "/byProducerName?producerName=" + producerName;

	    try {
	        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
	        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
	    } catch (HttpStatusCodeException e) {
	        switch (e.getStatusCode().value()) {
	            case 404 -> throw new RuntimeException("Products not found for company " + companyName);
	            default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
	        }
	    }
	}

	@Override
	public List<Product> getProductsByOriginFromCompany(String companyName, String producerOrigin) {
	    String url = apiBaseUrl + "/specialFood/products/" + companyName
	               + "/byProducerOrigin?producerOrigin=" + producerOrigin;

	    try {
	        ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
	        return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
	    } catch (HttpStatusCodeException e) {
	        switch (e.getStatusCode().value()) {
	            case 404 -> throw new RuntimeException("Products not found for company " + companyName);
	            default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
	        }
	    }
	}
	
	@Override
	public List<Product> searchProductsByName(String productName) {
		String url = apiBaseUrl + "/specialFood/products/byName/" + productName;
        
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("Name of the product not found: " + productName);
                default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
            }
        }
	}
	
	@Override
	public List<Product> searchProductsByDescription(String description) {
		String url = apiBaseUrl + "/specialFood/products/byDescription/" + description;
        
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("Description of the product not found: " + description);
                default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
            }
        }
	}
	
	@Override
	public List<Product> searchProductsByProducerName(String producerName) {
		String url = apiBaseUrl + "/specialFood/products/byProducerName/" + producerName;
        
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("Producer name of the products not found: " + producerName);
                default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
            }
        }
	}
	
	@Override
	public List<Product> searchProductsByProducerOrigin(String producerOrigin) {
		String url = apiBaseUrl + "/specialFood/products/byProducerOrigin/" + producerOrigin;
        
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 404 -> throw new RuntimeException("Producer origin of the products not found: " + producerOrigin);
                default -> throw new RuntimeException("Failed to retrieve products: " + e.getStatusText());
            }
        }
	}

	@Override
	public void buyProducts(String token, long productId, int quantity) {
		String url = apiBaseUrl + "/specialFood/products/" + productId + "/purchase?quantity=" + quantity;
        
        try {
            restTemplate.postForObject(url, token, Void.class);
        } catch (HttpStatusCodeException e) {
            switch (e.getStatusCode().value()) {
                case 401 -> throw new RuntimeException("User not authenticated");
                case 404 -> throw new RuntimeException("Product not found");
                case 409 -> throw new RuntimeException("Not enough stock available");
                case 204 -> { /* Successful purchase */ }
                case 500 -> throw new RuntimeException("Internal server error while processing purchase");
                default -> throw new RuntimeException("Purchase failed with status code: " + e.getStatusCode());
            }
        }
	}

}