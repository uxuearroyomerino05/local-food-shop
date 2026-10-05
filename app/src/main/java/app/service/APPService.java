package app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import app.entity.Product;


public class APPService {
	
	// Simulating category and article repositories
	private static Map<Long, Product> productRepository = new HashMap<>();
	// ObjectMapper instance for JSON processing
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	// Get all products
	public List<Product> getAllProducts(){
		return new ArrayList<>(productRepository.values());
	}
	
	// Get all products as JSON
	public String getProductsAsJson() {
		try {
			return objectMapper.writeValueAsString(this.getAllProducts());
		} catch (JsonProcessingException e) {
			System.err.println("Error serializando productos a JSON: " + e.getMessage());
			return "[]"; //An empty JSON array in case of error
		}
	}
		
	// Add a product
	public void addProduct(Product product) {
		productRepository.put(product.getId(), product);
	}
	
}
