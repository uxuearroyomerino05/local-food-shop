
package specialFoods.dto;

import java.util.Objects;

public class ProductDTO {
	private long id;
	private String name, description;
	private String producerName, ProducerOrigin;
	private double price;
	private int availableQuantity;
	private String company;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(long id, String name, String description, String producerName, String producerOrigin, double price,
			int quantity, String company) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		ProducerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = quantity;
		this.company = company;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getProducerOrigin() {
		return ProducerOrigin;
	}

	public void setProducerOrigin(String producerOrigin) {
		ProducerOrigin = producerOrigin;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int quantity) {
		this.availableQuantity = quantity;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ProducerOrigin, description, id, name, price, producerName, availableQuantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductDTO other = (ProductDTO) obj;
		return Objects.equals(ProducerOrigin, other.ProducerOrigin) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(producerName, other.producerName) && availableQuantity == other.availableQuantity;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", producerName="
				+ producerName + ", ProducerOrigin=" + ProducerOrigin + ", price=" + price + ", quantity=" + availableQuantity
				+ "]";
	}

	
	
	
}
