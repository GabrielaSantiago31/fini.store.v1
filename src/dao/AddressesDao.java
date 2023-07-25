package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Address;
import models.User;

public final class AddressesDao {
	
	public static void add(Address address) {
		String sql = "INSERT INTO addresses(street, observation, address_number, neighborhood, city, state, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			
			statement.setObject(1, address.getLogradouro().toUpperCase());
			statement.setObject(2, address.getComplemento().toUpperCase());
			statement.setObject(3, address.getNumero().toUpperCase());
			statement.setObject(4, address.getBairro().toUpperCase());
			statement.setObject(5, address.getLocalidade().toUpperCase());
			statement.setObject(6, address.getUf().toUpperCase());
			statement.setObject(7, address.getCep().toUpperCase());
			statement.execute();
			
			ResultSet rs = statement.getGeneratedKeys(); 
			if (rs.next()){ address.setId(rs.getInt(1)); }
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void remove(int id) {
		String sql = "DELETE FROM addresses" + " WHERE id = ?";
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, id);
			statement.execute();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void update(Address address) {
		String sql = "UPDATE addresses SET street = ?, observation = ?, address_number = ?, neighborhood = ?, city = ?, state = ?, zip_code = ?" + " WHERE id = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			
			statement.setObject(1, address.getLogradouro());
			statement.setObject(2, address.getComplemento());
			statement.setObject(3, address.getNumero());
			statement.setObject(4, address.getBairro());
			statement.setObject(5, address.getLocalidade());
			statement.setObject(6, address.getUf());
			statement.setObject(7, address.getCep());
			statement.setObject(8, address.getId());
			statement.execute();
			statement.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<Address> listOfAddresses(){
		List<Address> listOfAddresses = new ArrayList<>();
		String sql = "SELECT * FROM addresses";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.execute();
			
			try(ResultSet resultSet = statement.getResultSet()){
				
				while(resultSet.next()) {
					Address address = new Address("","","","","","","");
					address.setCep(resultSet.getString("zip_code"));
					address.setLogradouro(resultSet.getString("street"));
					address.setComplemento(resultSet.getString("observation"));
					address.setBairro(resultSet.getString("neighborhood"));
					address.setLocalidade(resultSet.getString("city"));
					address.setUf(resultSet.getString("state"));
					address.setNumero(resultSet.getString("address_number"));
					listOfAddresses.add(address);
				}
				
				resultSet.close();
				return listOfAddresses;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Address getAnAddressByZipCode(String zipCode) {
		String sql = "SELECT * FROM addresses WHERE zip_code = ?";
		
		try(PreparedStatement statement = ConnectionFactory.getConnection().prepareStatement(sql)){
			statement.setObject(1, zipCode);
			statement.execute();
			
			try(ResultSet result = statement.getResultSet()){
				if(result.next()) {
					Address address = new Address(
							result.getString("zip_code"),
							result.getString("street"),
							result.getString("observation"),
							result.getString("neighborhood"),
							result.getString("city"),
							result.getString("state"),
							result.getString("address_number"));
							address.setId(result.getInt("id"));
					result.close();
					return address;
				}
				return null;
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void addressesValidation(Address address) {
		Address existingAddress = getAnAddressByZipCode(address.getCep());
		if(existingAddress != null) {
			address.setId(existingAddress.getId());
		}else {
			add(address);
		}
	}
	
}
