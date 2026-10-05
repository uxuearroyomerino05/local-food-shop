package specialFoods.external;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ProductSellerFactory {

    private Map<String, IProductSellerGateway> productSellerGateway = new HashMap<>();
    
    public ProductSellerFactory(FarCoopGateway farCoopGateway, APPGateway appGateway) {
    	productSellerGateway.put("FARCOOP", farCoopGateway);
    	productSellerGateway.put("APP", appGateway);
    }
    
    // Factory method to create the appropriate gateway based on the company name
    public IProductSellerGateway createGateway(String company) {
        return productSellerGateway.get(company.toUpperCase()); 
    }

    // You might want a method to register new gateways at runtime if needed
    public void registerGateway(String company, IProductSellerGateway gateway) {
        productSellerGateway.put(company.toUpperCase(), gateway);
    }
}