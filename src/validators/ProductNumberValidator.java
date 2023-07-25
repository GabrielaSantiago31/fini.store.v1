package validators;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import dao.ProductsDao;
import models.Product;

public class ProductNumberValidator {
	
	static Scanner sc = new Scanner(System.in);
	
	public static String productNumberValidator(String input) {
			try {
				List<Integer> productsNumbers = new ArrayList<>();
				
				for(Product p : ProductsDao.listOfProducts()) {
					productsNumbers.add(p.getId());
				}
				
				while(!Pattern.matches("[0-9]+", input) || !productsNumbers.contains(Integer.parseInt(input))) {
					
					System.out.println("Invalid number. Try again: ");
					input = sc.nextLine();
				
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			return input;
		}
	
}
