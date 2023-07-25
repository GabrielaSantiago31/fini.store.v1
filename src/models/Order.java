package models;

import java.time.LocalDateTime;
import java.util.Map;

public class Order {
	
	private int id;
	private Map<Product,Integer> productsIdAndQuantities;
	private User user;
	private LocalDateTime date;
	
	
	public Order(Map<Product, Integer> productsIdAndQuantities, User user,LocalDateTime localDateTime) {
		super();
		this.productsIdAndQuantities = productsIdAndQuantities;
		this.user = user;
		this.date = localDateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Product, Integer> getProductsIdAndQuantities() {
		return productsIdAndQuantities;
	}

	public void setProductsIdAndQuantities(Map<Product, Integer> productsIdAndQuantities) {
		this.productsIdAndQuantities = productsIdAndQuantities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}	

}
