package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import models.Address;

public class ZipCodeValidator {
	
	static Scanner sc = new Scanner(System.in);
	
	public static String zipCodeValidator(String cep) {
		
			try {
				while(generateAddressByZipCode(cep).getCep() == null) {
						System.out.println("Invalid number. Try again: ");
						cep = sc.nextLine();
					
				}
				return cep;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	public static Address generateAddressByZipCode(String input){
		
		Address address = null;
		
		HttpGet request = new HttpGet("https://viacep.com.br/ws/"+ input + "/json/");
		
		try(CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
				CloseableHttpResponse response = httpClient.execute(request)) {
			
			HttpEntity entity = response.getEntity();
			
			if(entity != null) {
				
				String result = EntityUtils.toString(entity);
				
				Gson gson = new Gson();
				
				address = gson.fromJson(result, Address.class);
				
			}
			
		}catch(Exception e) {
			System.out.println(e);
		
		}
		return address;
	}
}
