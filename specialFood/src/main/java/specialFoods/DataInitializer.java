/*
 *  The objects have been created by Copilot
 */

package specialFoods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import specialFoods.entity.Product;
import specialFoods.entity.User;
import specialFoods.dao.ProductRepository;
import specialFoods.dao.UserRepository;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(ProductRepository productRepository, UserRepository userRepository) {
		return args -> {
			
			logger.info("Saving initial data...");
			
			// Create some products
			Product product1 = new Product("Exotic Mango", "A juicy and sweet mango from tropical regions.", "Tropical Farms", "India", 15.99, 100, "APP");
			Product product2 = new Product("Organic Honey", "Pure organic honey harvested from wildflowers.", "Nature's Best", "USA", 9.49, 200, "FarCoop");
			Product product3 = new Product("Truffle Oil", "Gourmet truffle oil for enhancing your dishes.", "Gourmet Delights", "Italy", 25.75, 50, "APP");
			Product product4 = new Product("Saffron Threads", "Premium quality saffron threads for cooking.", "Spice World", "Iran", 45.00, 30, "FarCoop");
			Product product5 = new Product("Caviar", "Luxury caviar sourced from the finest sturgeons.", "Ocean Treasures", "Russia", 150.00, 20, "APP");
			Product product6 = new Product("Artisanal Cheese", "Handcrafted cheese with rich flavors.", "Cheese Masters", "France", 12.99, 80, "FarCoop");
			Product product7 = new Product("Gourmet Chocolate", "Decadent dark chocolate with a hint of sea salt.", "ChocoLuxe", "Belgium", 8.99, 150, "APP");
			Product product8 = new Product("Exotic Spices Set", "A collection of rare and exotic spices.", "Spice Route", "Morocco", 19.99, 60, "FarCoop");
			Product product9 = new Product("Premium Olive Oil", "Extra virgin olive oil from ancient groves.", "Olive Gold", "Greece", 22.50, 90, "APP");
			Product product10 = new Product("Wild Mushroom Mix", "A mix of wild mushrooms for gourmet cooking.", "Forest Fresh", "Canada", 14.75, 70, "FarCoop");
			Product product11 = new Product("Exotic Fruit Basket", "A basket filled with a variety of exotic fruits.", "Tropical Farms", "Various", 29.99, 40, "APP");
			Product product12 = new Product("Gourmet Nut Selection", "A selection of premium nuts and dried fruits.", "Nutty Delights", "USA", 17.49, 120, "FarCoop");
			
			productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6,
					product7, product8, product9, product10, product11, product12));
			
			logger.info("Products saved!");
			
			// Create some users
			User test = new User("A", "B");
			User batman = new User("batman", "darkknight");
			User superman = new User("superman", "manofsteel");
			User wonderWoman = new User("wonderman", "amazonprincess");
			User spiderman = new User("spiderman", "friendlyneighborhood");
			User captainMarvel = new User("captainmarvel", "higherfurtherfaster");
			User blackWidow = new User("blackwidow", "redroom");
			
			userRepository.saveAll(List.of(test, batman, superman, wonderWoman, spiderman, captainMarvel, blackWidow));		
			
			logger.info("Users saved!");
				
		};
	}
}