package app.entity;

import java.util.Objects;

public class Product {
	
	private long id;
	private String name;
	private String description;
	private String producerName;
	private String producerOrigin;
	private double price;
	private int availableQuantity;
	
	public Product() {
		
	}
	
	public Product(long id, String name, String description, String producerName, String producerOrigin, double price,
			int availableQuantity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		this.producerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = availableQuantity;
	}
	
	public Product(String name, String description, String producerName, String producerOrigin, double price,
			int availableQuantity, String company) {
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		this.producerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = availableQuantity;
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
		return producerOrigin;
	}

	public void setProducerOrigin(String producerOrigin) {
		this.producerOrigin = producerOrigin;
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

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", producerName="
				+ producerName + ", ProducerOrigin=" + producerOrigin + ", price=" + price + ", availableQuantity="
				+ availableQuantity+"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(producerOrigin, availableQuantity, description, id, name, price, producerName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return this.id == other.id;
	}
	
	
}
