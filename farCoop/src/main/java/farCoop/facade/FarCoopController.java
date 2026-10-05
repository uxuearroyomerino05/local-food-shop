package farCoop.facade;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import farCoop.entity.Product;
import farCoop.service.FarCoopService;

@RestController
@RequestMapping("/farcoop")
public class FarCoopController {
	
	private final FarCoopService farCoopService;

 
 public FarCoopController(FarCoopService farCoopService) {
 	this.farCoopService = farCoopService;
 }
 
 //Get all products
 @GetMapping("/products")
 public ResponseEntity<List<Product>> getAllProducts() {
     try {
         List<Product> products = farCoopService.getAllProducts();
         if (products.isEmpty()) {
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
         return new ResponseEntity<>(products, HttpStatus.OK);
     } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
     }
 }
}