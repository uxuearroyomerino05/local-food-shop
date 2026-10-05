/*
 *  The Operations have been created by Copilot and updated by the user.
 */

package specialFoods.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import specialFoods.dto.ProductDTO;
import specialFoods.entity.Product;
import specialFoods.entity.User;
import specialFoods.service.AuthService;
import specialFoods.service.SpecialFoodService;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/specialFood")
@Tag(name = "Special Food Controller", description = "Operations related to produts and purchases")
public class SpecialFoodController {
	
	private static final Logger logger = LoggerFactory.getLogger(SpecialFoodController.class);
	
	
	private final SpecialFoodService specialFoodService;
	private final AuthService authService;

	public SpecialFoodController(SpecialFoodService specialFoodService, AuthService authService) {
		this.specialFoodService = specialFoodService;
		this.authService = authService;
	}
	
	//refreshProducts - Simulated daily event at 6 A.M.
	@Operation(
	        summary = "Refresh external products",
	        description = "Downloads the list of products from external services and stores them in the database",
	        responses = {
	            @ApiResponse(responseCode = "200", description = "OK: Product lists refreshed successfully"),
	            @ApiResponse(responseCode = "503", description = "Service Unavailable: Failed to connect or retrieve data from external providers"),
	            @ApiResponse(responseCode = "500", description = "Internal Server Error: Unexpected error during processing")
	        }
	    )
	    @PostMapping("/refreshProducts")
	    public ResponseEntity<String> refreshProducts() {
	        try {
	            // El servicio contiene la lógica de llamar a la Factory y a los Gateways.
	            specialFoodService.refreshAllProducts();
	            
	            // Éxito:
	            return new ResponseEntity<>("Product lists from external providers refreshed successfully.", HttpStatus.OK);
	            
	        } catch (RuntimeException e) {
	            // Capturamos cualquier error en la capa de servicio (ej: fallo de conexión al socket de APP)
	            System.err.println("Error refreshing products: " + e.getMessage());
	            // Usamos 503 Service Unavailable si se asume que es un fallo en la conexión externa.
	            return new ResponseEntity<>("Failed to refresh products from external providers.", HttpStatus.SERVICE_UNAVAILABLE);
	            
	        } catch (Exception e) {
	            // Capturamos cualquier otro error inesperado.
	            System.err.println("Unexpected internal error during product refresh: " + e.getMessage());
	            return new ResponseEntity<>("Internal server error during product refresh.", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	//getProductsByCompany - FarCoop or APP
	@Operation(
			summary = "Get all products by company",
			description = "Returns a list of all products from FarCoop",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of products retrieved successfully"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@GetMapping("/products/{company}")
		public ResponseEntity<List<ProductDTO>> getProductsByCompany(
				@Parameter(name = "company", description = "Company name (FarCoop or APP)", required = true, example = "APP")
				@PathVariable("company") String company) {
			try {
				List<Product> products = specialFoodService.getAllProductsFromCompany(company);
				
				List<ProductDTO> dtos = new ArrayList<>();
				products.forEach(product -> dtos.add(productToDTO(product)));
				
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//searchProductByName
	@Operation(
			summary = "Get produts of a company by name",
			description = "Returns a list of all produts of the company for a given name",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of products retrieved successfully"),
				@ApiResponse(responseCode = "204", description = "No Content: no product with that name"),
				@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
				@ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@GetMapping("/products/byName/{name}")
		public ResponseEntity<List<ProductDTO>> getProductsByName(
				@Parameter(name = "name", description = "Name of the product", required = true, example = "Exotic Mango")
				@PathVariable("name") String name) {
			try {
				
				List<Product> products = specialFoodService.getProductsByName(name);
							
				if (products.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				
				List<ProductDTO> dtos = new ArrayList<>();
				products.forEach(product -> dtos.add(productToDTO(product)));
				
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} catch (RuntimeException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//searchProductByDescription
	@Operation(
			summary = "Get produts by description",
			description = "Returns a list of all produts for a given description",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of products retrieved successfully"),
				@ApiResponse(responseCode = "204", description = "No Content: no product with that description"),
				@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
				@ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@GetMapping("/products/byDescription/{description}")
		public ResponseEntity<List<ProductDTO>> getProductsByDescription(
				@Parameter(name = "description", description = "Description of the product", required = true, example = "A juicy and sweet mango from tropical regions.")
				@PathVariable("description") String description) {
			try {
				
				List<Product> products = specialFoodService.getProductsByDescription(description);
							
				if (products.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				
				List<ProductDTO> dtos = new ArrayList<>();
				products.forEach(product -> dtos.add(productToDTO(product)));
				
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} catch (RuntimeException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//searchProductByProducerName
	@Operation(
			summary = "Get produts by producer name",
			description = "Returns a list of all produts for a given producer name",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of products retrieved successfully"),
				@ApiResponse(responseCode = "204", description = "No Content: no product with that producer name"),
				@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
				@ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@GetMapping("/products/byProducerName/{producerName}")
		public ResponseEntity<List<ProductDTO>> getProductsByProducerName(
				@Parameter(name = "producerName", description = "Producer name of the product", required = true, example = "Tropical Farms")
				@PathVariable("producerName") String producerName) {
			try {
				
				List<Product> products = specialFoodService.getProductsByProducerName(producerName);
							
				if (products.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				
				List<ProductDTO> dtos = new ArrayList<>();
				products.forEach(product -> dtos.add(productToDTO(product)));
				
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} catch (RuntimeException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//searchProductByProducerOrigin
	@Operation(
			summary = "Get produts by producer origin",
			description = "Returns a list of all produts for a given producer origin",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: List of products retrieved successfully"),
				@ApiResponse(responseCode = "204", description = "No Content: no product with that producer origin"),
				@ApiResponse(responseCode = "400", description = "Bad Request: Currency not supported"),
				@ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@GetMapping("/products/byProducerOrigin/{producerOrigin}")
		public ResponseEntity<List<ProductDTO>> getProductsByProducerOrigin(
				@Parameter(name = "producerOrigin", description = "Producer origin of the product", required = true, example = "India")
				@PathVariable("producerOrigin") String producerOrigin) {
			try {
				
				List<Product> products = specialFoodService.getProductsByProducerOrigin(producerOrigin);
							
				if (products.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
				
				List<ProductDTO> dtos = new ArrayList<>();
				products.forEach(product -> dtos.add(productToDTO(product)));
				
				return new ResponseEntity<>(dtos, HttpStatus.OK);
			} catch (RuntimeException e) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	//---NEW OPERATIONS---
	
	//getAllProducts
	@Operation(
		    summary = "Get all products",
		    description = "Returns all available products from all companies",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Products retrieved"),
		        @ApiResponse(responseCode = "204", description = "No Content: No products found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
		@GetMapping("/products")
		public ResponseEntity<List<ProductDTO>> getAllProducts() {
		    try {
		        List<Product> products = specialFoodService.getAllProducts();

		        if (products.isEmpty()) {
		            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		        }

		        List<ProductDTO> dtos = new ArrayList<>();
		        products.forEach(p -> dtos.add(productToDTO(p)));

		        return new ResponseEntity<>(dtos, HttpStatus.OK);
		    } catch (Exception e) {
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
	//getProductById
	@Operation(
		    summary = "Get product by ID",
		    description = "Returns the details of a single product",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Product found"),
		        @ApiResponse(responseCode = "404", description = "Not Found: Product not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
		@GetMapping("/products/id/{id}")
		public ResponseEntity<ProductDTO> getProductById(
		        @Parameter(name = "id", description = "Product ID", example = "5")
		        @PathVariable("id") long id) {

		    try {
		        return specialFoodService.getProductById(id)
		                .map(product -> new ResponseEntity<>(productToDTO(product), HttpStatus.OK))
		                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		    } catch (Exception e) {
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
	//getProductsByNameFromCompany
	@Operation(
		    summary = "Get products by name and company",
		    description = "Returns a list of products from a specific company filtered by product name",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Products retrieved successfully"),
		        @ApiResponse(responseCode = "204", description = "No Content: No products match the given name"),
		        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid parameters"),
		        @ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
	@GetMapping("/products/{company}/byName")
	public ResponseEntity<List<ProductDTO>> getProductsByNameFromCompany(
			@Parameter(
			        name = "company",
			        description = "Company name (APP or Farcoop)",
			        example = "APP"
			    )
	        @PathVariable("company") String company,
	        @Parameter(
	                name = "name",
	                description = "Product name filter",
	                example = "Mango"
	            )
	        @RequestParam("name") String name) {

	    try {
	        List<Product> products =
	                specialFoodService.getProductsByNameFromCompany(company, name);

	        if (products.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        List<ProductDTO> dtos = new ArrayList<>();
	        products.forEach(p -> dtos.add(productToDTO(p)));

	        return new ResponseEntity<>(dtos, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//getProductsByDescriptionFromCompany
	@Operation(
		    summary = "Get products by description and company",
		    description = "Returns a list of products from a specific company filtered by product description",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Products retrieved successfully"),
		        @ApiResponse(responseCode = "204", description = "No Content: No products match the given description"),
		        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid parameters"),
		        @ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
	@GetMapping("/products/{company}/byDescription")
	public ResponseEntity<List<ProductDTO>> getProductsByDescriptionFromCompany(
			@Parameter(
			        name = "company",
			        description = "Company name (APP or Farcoop)",
			        example = "FARCOOP"
			    )
	        @PathVariable("company") String company,
	        @Parameter(
	                name = "description",
	                description = "product description filter",
	                example = "A juicy and sweet mango from tropical regions."
	            )
	        @RequestParam("description") String description) {

	    try {
	        List<Product> products =
	                specialFoodService.getProductsByDescriptionFromCompany(company, description);

	        if (products.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        List<ProductDTO> dtos = new ArrayList<>();
	        products.forEach(p -> dtos.add(productToDTO(p)));

	        return new ResponseEntity<>(dtos, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	//getProductsByProducerNameFromCompany
	@Operation(
		    summary = "Get products by producer name and company",
		    description = "Returns a list of products from a specific company filtered by producer name",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Products retrieved successfully"),
		        @ApiResponse(responseCode = "204", description = "No Content: No products match the given producer name"),
		        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid parameters"),
		        @ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
	@GetMapping("/products/{company}/byProducerName")
	public ResponseEntity<List<ProductDTO>> getProductsByProducerNameFromCompany(
			@Parameter(
			        name = "company",
			        description = "Company name (APP or Farcoop)",
			        example = "APP"
			    )
	        @PathVariable("company") String company,
	        @Parameter(
	                name = "producerName",
	                description = "Producer or farm name",
	                example = "Tropical Farms"
	            )
	        @RequestParam("producerName") String producerName) {

	    try {
	        List<Product> products =
	                specialFoodService.getProductsByProducerNameFromCompany(company, producerName);

	        if (products.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        List<ProductDTO> dtos = new ArrayList<>();
	        products.forEach(p -> dtos.add(productToDTO(p)));

	        return new ResponseEntity<>(dtos, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//getProductsByProducerOriginFromCompany
	@Operation(
		    summary = "Get products by producer origin and company",
		    description = "Returns a list of products from a specific company filtered by producer origin",
		    responses = {
		        @ApiResponse(responseCode = "200", description = "OK: Products retrieved successfully"),
		        @ApiResponse(responseCode = "204", description = "No Content: No products match the given producer origin"),
		        @ApiResponse(responseCode = "400", description = "Bad Request: Invalid parameters"),
		        @ApiResponse(responseCode = "404", description = "Not Found: Company not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
	@GetMapping("/products/{company}/byProducerOrigin")
	public ResponseEntity<List<ProductDTO>> getProductsByProducerOriginFromCompany(
			@Parameter(
			        name = "company",
			        description = "Company name (APP or Farcoop)",
			        example = "APP"
			    )
	        @PathVariable("company") String company,
	        @Parameter(
	                name = "producerOrigin",
	                description = "Origin or location of the producer",
	                example = "India"
	            )
	        @RequestParam("producerOrigin") String producerOrigin) {

	    try {
	        List<Product> products =
	                specialFoodService.getProductsByProducerOriginFromCompany(company, producerOrigin);

	        if (products.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        List<ProductDTO> dtos = new ArrayList<>();
	        products.forEach(p -> dtos.add(productToDTO(p)));

	        return new ResponseEntity<>(dtos, HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	//----------
	
	
	//buyProduct
	@Operation(
			summary = "Buy a product",
			description = "Creates a purchase for a given product and user",
			responses = {
				@ApiResponse(responseCode = "200", description = "OK: Purchase created successfully"),
				@ApiResponse(responseCode = "400", description = "Bad Request: Invalid input data"),
				@ApiResponse(responseCode = "401", description = "Unauthorized: Invalid or missing token"),
				@ApiResponse(responseCode = "500", description = "Internal server error")
			}
		)
		 
		@PostMapping("/products/{productId}/purchase")
		public ResponseEntity<Void> buyProduct(
				@Parameter(name = "productId", description = "ID of the product to buy", required = true, example = "10")
				@PathVariable("productId") long productId,
				@Parameter(name = "quantity", description = "Quantity of the product", required = true, example = "3")
				@RequestParam("quantity") int quantity,
				@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Authorization token in plain text", required = true)
	    		@RequestBody String token) {
			try {
				
				User user = authService.getUserByToken(token);
				logger.info("Token received: {}", token);
				logger.info("User retrieved from token: {}", user != null ? user.getUsername() : "null");
		    	
		    	if (user == null) {
		    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		    	}
		    	logger.info("User {} is authorized to make a purchase.", user.getUsername());
				
				specialFoodService.buyProduct(user, productId, quantity);
				logger.info("Purchase completed: User {} bought {} units of product with id {}", user.getUsername(), quantity, productId);
				
				return new ResponseEntity<>(HttpStatus.OK);
			} catch (RuntimeException e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	// Converts a Product to a ProductDTO
	private ProductDTO productToDTO(Product product) {
		return new ProductDTO(product.getId(),
							  product.getName(),
							  product.getDescription(),
							  product.getProducerName(),
							  product.getProducerOrigin(),
							  product.getPrice(),
							  product.getAvailableQuantity(),
							  product.getCompany());	
							  
	}
	
}
