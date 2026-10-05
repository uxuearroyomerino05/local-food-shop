package farCoop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import farCoop.dao.ProductRepository;
import farCoop.entity.Product;

@Service
public class FarCoopService {
	
	private final ProductRepository productRepository;

    public FarCoopService(ProductRepository productRepository) {
    	this.productRepository = productRepository;
    }
    
 // Get all products
 	public List<Product> getAllProducts() {
 		return new ArrayList<>(productRepository.findAll());
 	}
	
}
