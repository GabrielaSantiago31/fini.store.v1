package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Address;
import models.User;

public final class UsersDao {
	
	public static void add(User user) {
		String sql = "INSERT INTO users(user_name, last_name, email, phone, cpf, birthdate, user_password, id_address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, user.getName());
			statement.setObject(2, user.getLastName());
			statement.setObject(3, user.getEmail());
			statement.setObject(4, user.getPhone());
			statement.setObject(5, user.getCpf());
			statement.setObject(6, user.getBirthdate());
			statement.setObject(7, user.getPassword());
			statement.setObject(8, user.getAddress().getId());
			statement.execute();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void remove(int id) {
		String sql = "DELETE FROM users" + " WHERE id = ?";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, id);
			statement.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void update(User user) {
		String sql = "UPDATE users SET user_name = ?, last_name = ?, email = ?, phone = ?, birthdate = ?, user_password = ?" + " WHERE id = ?";
				 
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setString(1, user.getName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPhone());
			statement.setString(5, user.getBirthdate());
			statement.setString(6, user.getPassword());
			statement.setObject(7, user.getId());
			statement.execute();
			statement.close();
			
			System.out.println("User has been successfully updated! \n");
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<User> listOfUsers(){
		List<User> listOfUsers = new ArrayList<>();
		String sql = "SELECT * FROM users u INNER JOIN addresses a ON u.id_address = a.id";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
				
				while(resultSet.next()) {
					User user = new User("","","","","","", null,"");
					user.setName(resultSet.getString("user_name"));
					user.setLastName(resultSet.getString("last_name"));
					user.setEmail(resultSet.getString("email"));
					user.setPhone(resultSet.getString("phone"));
					user.setCpf(resultSet.getString("cpf"));
					user.setBirthdate(resultSet.getString("birthdate"));
					user.setAddress(
							new Address(
									
									resultSet.getString("street"),
									resultSet.getString("observation"),
									resultSet.getString("address_number"),
									resultSet.getString("neighborhood"),
									resultSet.getString("city"),
									resultSet.getString("state"),
									resultSet.getString("zip_code")
						
									)
							);
					user.setPassword(resultSet.getString("user_password"));
					listOfUsers.add(user);
				}
				
				resultSet.close();
				return listOfUsers;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static User returnAnUserByEmail(String email, String password) {
		
		String sql = "SELECT * FROM users u INNER JOIN addresses a ON u.id_address = a.id WHERE u.email = ? and u.user_password = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, email);
			statement.setObject(2, password);
			statement.execute();
			
			try(ResultSet result = statement.getResultSet()){
				Address ad;
				if(result.next()) {
					User user = new User(
							result.getString("user_name"),
							result.getString("last_name"),
							result.getString("email"),
							result.getString("phone"),
							result.getString("cpf"),
							result.getString("birthdate"),
							ad = new Address(
									
									result.getString("zip_code"),
									result.getString("street"),
									result.getString("observation"),
									result.getString("neighborhood"),
									result.getString("city"),
									result.getString("state"),
									result.getString("address_number")
									),
							result.getString("user_password"));
							ad.setId(result.getInt("id_address"));
							user.setId(result.getInt("id"));
					result.close();
					return user;
				}
				return null;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
