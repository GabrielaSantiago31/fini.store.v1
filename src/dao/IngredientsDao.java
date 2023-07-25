package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Ingredient;
import models.Product;

public final class IngredientsDao {
	public static void add(Ingredient ingredient) {
		String sql = "INSERT INTO ingredients(ingredient_name, quantity) VALUES (?, ?)";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			statement.setObject(1, ingredient.getName());
			statement.setObject(2, ingredient.getQuantity());
			statement.execute();
			
			ResultSet rs = statement.getGeneratedKeys(); 
			ingredient.setId(rs.next()? rs.getInt(1):0);
			
			statement.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void remove(int id) {
		String sql = "DELETE FROM ingredients" + " WHERE id = ?";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, id);
			statement.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void update(int id, int quantity) {
		String sql = "UPDATE ingredients SET quantity = ?" + " WHERE id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql, ResultSet.CONCUR_UPDATABLE)){
			
			statement.setObject(1, id);
			statement.setObject(2, quantity);
			statement.execute();
			statement.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<Ingredient> listOfIngredients(){
		List<Ingredient> listOfIngredients = new ArrayList<>();
		String sql = "SELECT * FROM ingredients";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
				
				while(resultSet.next()) {
					Ingredient ingredient = new Ingredient("",0);
					ingredient.setId(resultSet.getInt("id"));
					ingredient.setName(resultSet.getString("ingredient_name"));
					ingredient.setQuantity(resultSet.getInt("quantity"));
					listOfIngredients.add(ingredient);
				}
				
				resultSet.close();
				return listOfIngredients;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Ingredient returnAnIngredientById(int id) {
		String sql = "SELECT * FROM ingredients WHERE id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, id);
			statement.execute();
		
		try(ResultSet result = statement.getResultSet()){
			if(result.next()) {
				Ingredient ingredient = new Ingredient(
						result.getString("ingredient_name"),
						result.getInt("quantity"));
				result.close();
				return ingredient;
			}
			return null;
		}
		
	}catch(SQLException e) {
		throw new RuntimeException(e);
			}
		}
}
