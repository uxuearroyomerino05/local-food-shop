package client.proxies;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.data.Credentials;
import client.data.Product;

public class HttpServiceProxy implements ISFServiceProxy {
    
	private String baseUrl = System.getProperty("API_BASE_URL");
    private final HttpClient httpClient = HttpClient.newBuilder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String login(Credentials credentials) {
        try {
            String jsonBody = objectMapper.writeValueAsString(credentials);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return response.body(); // Returns the token
            } else {
                throw new RuntimeException("Login failed with status: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during login", e);
        }
    }

    @Override
    public void logout(String token) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/auth/logout"))
                    .POST(HttpRequest.BodyPublishers.ofString(token))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (Exception e) {
            System.err.println("Logout error: " + e.getMessage());
        }
    }
    
    @Override
    public List<Product> getAllProducts() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/specialFood/products"))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
            } else {
                throw new RuntimeException("Failed to retrieve all products: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all products", e);
        }
    }

    @Override
    public Optional<Product> getProductById(long productId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/specialFood/products/id/" + productId))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Product product = objectMapper.readValue(response.body(), Product.class);
                return Optional.ofNullable(product);
            } else if (response.statusCode() == 404) {
                return Optional.empty();
            } else {
                throw new RuntimeException("Failed to retrieve product: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product by id", e);
        }
    }
    
    @Override
    public List<Product> getProductsByCompany(String companyName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/specialFood/products/" + companyName))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
            } else if (response.statusCode() == 404) {
                throw new RuntimeException("Company not found: " + companyName);
            } else {
                throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products by company", e);
        }
    }

	@Override
	public List<Product> getProductsByNamefromCompany(String companyName, String productName) {
	    try {
	        String url = baseUrl + "/specialFood/products/" + companyName + "/byName?name=" + productName;
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();
	
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	
	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Products not found for company " + companyName);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving products by name from company", e);
	    }
	}
	
	@Override
	public List<Product> getProductsByDescriptionFromCompany(String companyName, String description) {
	    try {
	        String url = baseUrl + "/specialFood/products/" + companyName + "/byDescription?description=" + description;
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();
	
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	
	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Products not found for company " + companyName);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving products by description from company", e);
	    }
	}
	
	@Override
	public List<Product> getProductsByProducerFromCompany(String companyName, String producerName) {
	    try {
	        String url = baseUrl + "/specialFood/products/" + companyName + "/byProducerName?producerName=" + producerName;
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();
	
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	
	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Products not found for company " + companyName);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving products by producer from company", e);
	    }
	}
	
	@Override
	public List<Product> getProductsByOriginFromCompany(String companyName, String producerOrigin) {
	    try {
	        String url = baseUrl + "/specialFood/products/" + companyName + "/byProducerOrigin?producerOrigin=" + producerOrigin;
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();
	
	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
	
	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Products not found for company " + companyName);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error retrieving products by origin from company", e);
	    }
	}
	
	@Override
    public List<Product> searchProductsByName(String productName) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/specialFood/products/byName/" + productName))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Search failed", e);
        }
    }

	@Override
	public List<Product> searchProductsByDescription(String description) {
	    try {
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(baseUrl + "/specialFood/products/byDescription/" + description))
	                .GET()
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Description of the product not found: " + description);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error searching products by description", e);
	    }
	}

	@Override
	public List<Product> searchProductsByProducerName(String producerName) {
	    try {
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(baseUrl + "/specialFood/products/byProducerName/" + producerName))
	                .GET()
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Producer name of the products not found: " + producerName);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error searching products by producer name", e);
	    }
	}

	@Override
	public List<Product> searchProductsByProducerOrigin(String producerOrigin) {
	    try {
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(baseUrl + "/specialFood/products/byProducerOrigin/" + producerOrigin))
	                .GET()
	                .build();

	        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

	        if (response.statusCode() == 200) {
	            return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
	        } else if (response.statusCode() == 404) {
	            throw new RuntimeException("Producer origin of the products not found: " + producerOrigin);
	        } else {
	            throw new RuntimeException("Failed to retrieve products: " + response.statusCode());
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error searching products by producer origin", e);
	    }
	}
	
	@Override
    public void buyProducts(String token, long productId, int quantity) {
        try {
            String url = baseUrl + "/specialFood/products/" + productId + "/purchase?quantity=" + quantity;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(token))
                    .build();

            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200 && response.statusCode() != 204) {
                throw new RuntimeException("Purchase failed: " + response.statusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during purchase", e);
        }
    }

}