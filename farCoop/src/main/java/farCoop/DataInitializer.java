/*
 *  The objects have been created by Copilot
 */

package farCoop;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import farCoop.entity.Product;
import farCoop.dao.ProductRepository;

@Configuration
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	
    @Bean
    CommandLineRunner initData(ProductRepository productRepository) {
		return args -> {
			
			logger.info("Saving initial data...");
			
			// Create some products
			Product tomato = new Product("Tomato", "Fresh organic tomatoes", "Green Farm", "Local", 2.5, 100);
			Product potato = new Product("Potato", "Organic potatoes", "Healthy Harvest", "Local", 1.8, 150);
			Product carrot = new Product("Carrot", "Crunchy organic carrots", "Veggie Delight", "Local", 2.0, 120);
			Product lettuce = new Product("Lettuce", "Fresh green lettuce", "Green Farm", "Local", 1.5, 80);
			Product cucumber = new Product("Cucumber", "Organic cucumbers", "Healthy Harvest", "Local", 2.2, 90);
			Product fish = new Product("Salmon", "Fresh Atlantic salmon", "Ocean's Best", "Norway", 15.0, 50);
			Product chips = new Product("Potato Chips", "Crispy salted potato chips", "Snack Time", "USA", 3.0, 200);
			Product soda = new Product("Cola", "Refreshing cola drink", "Fizz Co.", "USA", 1.5, 180);
			Product chocolate = new Product("Chocolate Bar", "Delicious milk chocolate", "Sweet Treats", "Switzerland", 2.5, 160);
			Product coffee = new Product("Coffee Beans", "Premium Arabica coffee beans", "Brew Masters", "Colombia", 12.0, 140);
			Product tea = new Product("Green Tea", "Organic green tea leaves", "Tea Time", "China", 8.0, 130);
			Product honey = new Product("Honey", "Pure natural honey", "Bee Good", "Local", 10.0, 110);
			Product almond = new Product("Almonds", "Raw organic almonds", "Nutty Delights", "USA", 9.0, 170);
			Product walnut = new Product("Walnuts", "Fresh organic walnuts", "Nutty Delights", "USA", 11.0, 150);
			Product salad = new Product("Caesar Salad", "Ready-to-eat Caesar salad", "Fresh Bites", "Local", 6.0, 160);
			
			productRepository.saveAll(List.of(tomato, potato, carrot, lettuce, cucumber,
					fish, chips, soda, chocolate, coffee, tea, honey, almond, walnut, salad));
			
			logger.info("Products saved!");
			
				
		};
	}
}