package specialFoods.entity;

import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String producerName;
	
	@Column(nullable = false)
	private String producerOrigin;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false)
	private int availableQuantity;
	
	@Column(nullable = false)
	private String company;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Purchase> purchases = new ArrayList<>();
	
	public Product() {
		
	}
	
	public Product(long id, String name, String description, String producerName, String producerOrigin, double price,
			int availableQuantity, String company) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		this.producerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.company = company;
	}
	
	public Product(String name, String description, String producerName, String producerOrigin, double price,
			int availableQuantity, String company) {
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		this.producerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.company = company;
	}
	
	public Product(long id, String name, String description, String producerName, String producerOrigin, double price,
			int availableQuantity, String company, ArrayList<Purchase> purchases) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.producerName = producerName;
		this.producerOrigin = producerOrigin;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.company = company;
		this.purchases = purchases;
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
	
	public int getCurrentQuantity() {
		return availableQuantity-purchases.size();
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	
	public ArrayList<Purchase> getPurchases() {
		return purchases.isEmpty() ? null : new ArrayList<Purchase>(purchases);
	}
	
	public void setPurchases(ArrayList<Purchase> purchases) {
		this.purchases = purchases;
	}

	
	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
	}
	
	public void removePurchase(Purchase purchase) {
		this.purchases.remove(purchase);
	}

	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", producerName="
				+ producerName + ", ProducerOrigin=" + producerOrigin + ", price=" + price + ", availableQuantity="
				+ availableQuantity + ", company="+company+"]";
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
