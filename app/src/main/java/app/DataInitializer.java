
package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.entity.Product;
import app.service.APPService;


public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
	

	public static void initializeData(APPService appService){
			
			// Create some products
			Product banana = new Product(1, "Banana", "A yellow fruit", "FreshFarms", "Ecuador", 0.5, 100);
			Product apple = new Product(2, "Apple", "A red fruit", "OrchardGoods", "USA", 0.7, 150);
			Product orange = new Product(3, "Orange", "A citrus fruit", "CitrusWorld", "Spain", 0.6, 120);
			Product mango = new Product(4, "Mango", "A tropical fruit", "TropicalDelights", "India", 1.5, 80);
			Product grape = new Product(5, "Grape", "A small fruit", "VineyardFresh", "Italy", 2.0, 90);
			Product eggs = new Product(6, "Eggs", "A dozen eggs", "FarmFresh", "USA", 3.0, 200);
			Product milk = new Product(7, "Milk", "1 liter of milk", "DairyPure", "USA", 1.2, 180);
			Product bread = new Product(8, "Bread", "Whole wheat bread", "Baker'sBest", "USA", 2.5, 160);
			Product cheese = new Product(9, "Cheese", "Cheddar cheese", "CheeseMakers", "France", 4.0, 140);
			Product yogurt = new Product(10, "Yogurt", "Greek yogurt", "YogurtLand", "Greece", 1.8, 130);
			Product chicken = new Product(11, "Chicken", "1 kg chicken breast", "PoultryFarm", "USA", 5.0, 110);
			Product rice = new Product(12, "Rice", "1 kg basmati rice", "RiceWorld", "India", 2.2, 170);
			Product pasta = new Product(13, "Pasta", "500g spaghetti", "PastaHouse", "Italy", 1.5, 190);
			Product cereal = new Product(14, "Cereal", "Breakfast cereal", "CerealKing", "USA", 3.5, 150);
			Product juice = new Product(15, "Juice", "1 liter orange juice", "JuiceWorld", "USA", 2.0, 160);
			
			appService.addProduct(banana);
			appService.addProduct(apple);
			appService.addProduct(orange);
			appService.addProduct(mango);
			appService.addProduct(grape);
			appService.addProduct(eggs);
			appService.addProduct(milk);
			appService.addProduct(bread);
			appService.addProduct(cheese);
			appService.addProduct(yogurt);
			appService.addProduct(chicken);
			appService.addProduct(rice);
			appService.addProduct(pasta);
			appService.addProduct(cereal);
			appService.addProduct(juice);
			
			logger.info("Products saved!");					
	}
}