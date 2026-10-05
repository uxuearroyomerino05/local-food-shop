package specialFoods.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private long date;
	
	@Column(nullable = false)
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public Purchase() {
	}
	
	public Purchase(long date, int quantity, Product product, User user) {
		this.date = date;
		this.product = product;
		this.user = user;
		this.quantity = quantity;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getAmount() {
		return quantity;
	}
	
	public void setAmount(int quantity) {
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Purchase [date=" + date + ", amount=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity, date, product, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Purchase other = (Purchase) obj;
		return Double.doubleToLongBits(quantity) == Double.doubleToLongBits(other.quantity) && date == other.date;
	}
	
	
	
}
