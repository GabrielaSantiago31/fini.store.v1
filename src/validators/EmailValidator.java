package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailValidator {
	static Scanner sc = new Scanner(System.in);
	
	public static String emailValidator(String input) {
		
		try {
			while(!Pattern.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", input)) {
				System.out.println("Invalid email. Try again: ");
				input = sc.nextLine().toUpperCase();
			}
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return input;
	}
}
