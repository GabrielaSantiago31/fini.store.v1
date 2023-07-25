package models;

import java.util.List;

public class Product {
	
	private int id;
	private String name;
	private String flavor;
	private double price;
	private int gramsPerPackage;
	private String category;
	private List<String> productIngredients;
	
	public Product(String name, String flavor, double price, int gramsPerPackage, String category) {
		super();
		this.name = name;
		this.flavor = flavor;
		this.price = price;
		this.gramsPerPackage = gramsPerPackage;
		this.category = category;
		
	}
	

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFlavor() {
		return flavor;
	}


	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getGramsPerPackage() {
		return gramsPerPackage;
	}


	public void setGramsPerPackage(int gramsPerPackage) {
		this.gramsPerPackage = gramsPerPackage;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}
	
	


	public List<String> getProductIngredients() {
		return productIngredients;
	}


	public void setProductIngredients(List<String> productIngredients) {
		this.productIngredients = productIngredients;
	}


	@Override
	public String toString() {
		return "" + id + ";" + name + ";" + flavor + ";" + price + ";" + gramsPerPackage + ";"
				+ category + "\n";
	}
	
}
