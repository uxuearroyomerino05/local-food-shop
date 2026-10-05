package specialFoods.external;

import specialFoods.dto.ProductDTO;
import java.util.List;

public interface IProductSellerGateway {
    List<ProductDTO> getProducts();
}
