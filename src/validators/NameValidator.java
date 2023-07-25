package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

public class NameValidator {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static String nameValidator(String input) {
	   try {
		   while(!Pattern.matches("^[\\p{L} .'-]+[/^\\S+$/]+$", input)) {
				
				System.out.println("Invalid name! Type only letters and no space in the end!");
				input = sc.nextLine().toUpperCase();
			} 
		   
	   }catch(Exception e) {
		   
		   e.printStackTrace();
	   }
		
		return input;
	}
	
}
