package specialFoods.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import specialFoods.entity.Purchase;

public interface PurchaseRepository   extends JpaRepository<Purchase, Long> {

}
