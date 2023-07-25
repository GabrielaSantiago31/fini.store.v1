package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import models.Address;
import models.Order;
import models.Product;
import models.User;

public final class OrdersDao {
	
	public static void add(Order order) {
		String sql = "INSERT INTO orders(id_user,date) VALUES (?, ?)";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			statement.setObject(1, order.getUser().getId());
			statement.setObject(2, order.getDate());
			statement.execute();
			
			ResultSet rs = statement.getGeneratedKeys(); 
			order.setId(rs.next()? rs.getInt(1):0);
			
			statement.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		String sqlOrderProduct = "INSERT INTO order_products(order_id, product_id, price, quantity) VALUES (?, ?, ?, ?)";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sqlOrderProduct)){
			
			for(Entry<Product, Integer> orders : order.getProductsIdAndQuantities().entrySet()) {
				statement.setObject(1, order.getId());
				statement.setObject(2, orders.getKey().getId());
				statement.setObject(3, orders.getKey().getPrice());
				statement.setObject(4, orders.getValue());
				statement.execute();
			}
			statement.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void remove(int id) {
		String sql = "DELETE FROM orders" + " WHERE id = ?";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, id);
			statement.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
			
}
