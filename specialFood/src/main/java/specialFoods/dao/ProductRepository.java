
package specialFoods.dao;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import specialFoods.entity.Product;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

	//INTERNAL USE
	ArrayList<Product> findByCompanyContainingIgnoreCase(String company);
	ArrayList<Product> findByNameContainingIgnoreCase(String name);
	ArrayList<Product> findByDescriptionContainingIgnoreCase(String description);
	ArrayList<Product> findByProducerNameContainingIgnoreCase(String producerName);
	ArrayList<Product> findByProducerOriginContainingIgnoreCase(String producerOrigin);
	ArrayList<Product> findByCompanyAndNameContainingIgnoreCase(String company, String name);
	ArrayList<Product> findByCompanyAndDescriptionContainingIgnoreCase(String company, String description);
	ArrayList<Product> findByCompanyAndProducerNameContainingIgnoreCase(String company, String producerName);
	ArrayList<Product> findByCompanyAndProducerOriginContainingIgnoreCase(String company, String producerOrigin);
	
	//EXTERNAL USE
	Optional<Product> findByNameAndCompany(String name, String company);

}