package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.Ingredient;
import models.Product;


public final class ProductsDao {
	
	public static void add(Product product) {
	String sql = "INSERT INTO products(product_name, flavor, price, grams_package, category) VALUES (?, ?, ?, ?,?)";
	try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
		
		statement.setObject(1, product.getName());
		statement.setObject(2, product.getFlavor());
		statement.setObject(3, product.getPrice());
		statement.setObject(4, product.getGramsPerPackage());
		statement.setObject(5, product.getCategory());
		statement.execute();
		
		ResultSet rs = statement.getGeneratedKeys(); 
		product.setId(rs.next()? rs.getInt(1):0);
		
		statement.close();
		
		
	}catch(SQLException e) {
		throw new RuntimeException(e);
	}
}

	public static void remove(int id) {
		String sql = "DELETE FROM products" + " WHERE id = ?";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, id);
			statement.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void update(Product product) {
		String sql = "UPDATE products SET product_name = ?, flavor = ?, price = ?, grams_package = ?, category = ?" + " WHERE id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, product.getName());
			statement.setObject(2, product.getFlavor());
			statement.setObject(3, product.getPrice());
			statement.setObject(4, product.getGramsPerPackage());
			statement.setObject(5, product.getCategory());
			statement.setObject(6, product.getId());
			statement.execute();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<Product> listOfProducts(){
		List<Product> listOfProducts = new ArrayList<>();
		String sql = "SELECT * FROM products";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
				
				while(resultSet.next()) {
					Product product = new Product("","",0.0,0,"");
					product.setId(resultSet.getInt("id"));
					product.setName(resultSet.getString("product_name"));
					product.setFlavor(resultSet.getString("flavor"));
					product.setPrice(resultSet.getDouble("price"));
					product.setGramsPerPackage(resultSet.getInt("grams_package"));
					product.setCategory(resultSet.getString("category"));
					listOfProducts.add(product);
				}
				resultSet.close();
				return listOfProducts;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void printCatalog() {
		System.out.println("\n");
		for(Product u : listOfProducts()) {
			System.out.println("Code: " + u.getId());
			System.out.println("Product: " + u.getName());
			System.out.println("Flavor: " + u.getFlavor());
			System.out.println("Quantity: " + u.getGramsPerPackage());
			System.out.println("Price: " + u.getPrice());
			System.out.println("Category: " + u.getCategory());
			System.out.println("================================");
		}
		System.out.println("\n");
	}
	
	public static Product returnAProductById(int id) {
		String sql = "SELECT * FROM products p WHERE p.id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, id);
			statement.execute();
		
		try(ResultSet result = statement.getResultSet()){
			if(result.next()) {
				Product product = new Product(
						result.getString("product_name"),
						result.getString("flavor"),
						result.getDouble("price"),
						result.getInt("grams_package"),
						result.getString("category"));
						product.setId(result.getInt("id"));
				result.close();
				return product;
			}
			return null;
		}
		
	}catch(SQLException e) {
		throw new RuntimeException(e);
			}
	
		}
	
	public static boolean isProductAvailable(int idProduct) {
		String sql = "SELECT * FROM ingredients_product WHERE product_id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, idProduct);
			statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
				
				while(resultSet.next()) {
					Ingredient i = IngredientsDao.returnAnIngredientById(resultSet.getInt("ingredient_id"));
					if(i.getQuantity() < resultSet.getInt("quantity")) {
						return false;
					}
				}
				resultSet.close();	
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
		
	public static boolean isProductQuantityAvailable(int idProduct,int qtdProduct) {
		
			String sql = "SELECT * FROM ingredients_product WHERE product_id = ?";
			
			try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
				statement.setObject(1, idProduct);
				statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
					
					while(resultSet.next()) {
						Ingredient i = IngredientsDao.returnAnIngredientById(resultSet.getInt("ingredient_id"));
						if(i.getQuantity() < resultSet.getInt("quantity")*qtdProduct) {
							return false;
						}
					}
					resultSet.close();	
			}
			}catch(SQLException e) {
				throw new RuntimeException(e);
			}
			
			return true;
		}
	
	public static void  updateProductsIngredientsQuantity(int idProduct, int qtdProduct) {
		String sql = "SELECT * FROM ingredients_product WHERE product_id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, idProduct);
			statement.execute();
		
		try(ResultSet resultSet = statement.getResultSet()){
			
			while(resultSet.next()) {
				Ingredient i = IngredientsDao.returnAnIngredientById(resultSet.getInt("ingredient_id"));
				if(i.getQuantity() > 0) {
					IngredientsDao.update((i.getQuantity()-(resultSet.getInt("quantity")*qtdProduct)), resultSet.getInt("ingredient_id"));
				}
			}
		}
		
	}catch(SQLException e) {
		throw new RuntimeException(e);
		}
		
	}
	
	public static void addProductIngredients(int productId, String productName, int quantity) {
		
		String sql = "INSERT INTO ingredients_product(product_id, ingredient_id, quantity) VALUES (?, ?, ?)";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			for(Ingredient i : IngredientsDao.listOfIngredients()) {
				if(i.getName().equals(productName)) {
						statement.setObject(1,productId);
						statement.setObject(2,i.getId());
						statement.setObject(3,quantity);
						statement.execute();
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
}
