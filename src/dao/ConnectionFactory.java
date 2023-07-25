package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;

public final class ConnectionFactory {
	
	public static Connection getConnection() {
		try {
			Properties props = carregaPropriedades();
			String url = props.getProperty("dburl");
			Connection connection = DriverManager.getConnection(url, props);
			return connection;
		}catch(Exception e) {
			throw new DbException(e.getMessage());
		}
	}
	
	private static Properties carregaPropriedades() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		}catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
}
