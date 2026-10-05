package specialFoods.external;

import specialFoods.dto.ProductDTO;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class FarCoopGateway implements IProductSellerGateway {
	
	private final RestTemplate restTemplate;
	private static final String FARCOOP_PRODUCTS_URL = "http://localhost:8084/farcoop/products";
	
	public FarCoopGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate; 
    }
	
	public List<ProductDTO> getProducts() { 
        try {
            ResponseEntity<List<ProductDTO>> response = 
                restTemplate.exchange(
                    FARCOOP_PRODUCTS_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ProductDTO>>() {}
                );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // Return the list of DTO products
            	List<ProductDTO> dtoList = response.getBody(); 
                dtoList.forEach(dto -> dto.setCompany("FarCoop"));
                return dtoList;
            }
            
            // Failed response: empty list
            return Collections.emptyList();
            
        } catch (Exception e) {
            System.err.println("Error obtaining FarCoop products (DTO): " + e.getMessage());
            return Collections.emptyList();
        }
    }
}