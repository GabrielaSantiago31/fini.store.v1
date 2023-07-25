package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

public class DoubleValidator {
	
	static Scanner sc = new Scanner(System.in);
	
	public static String doubleValidator(String number) {
		try {
			while(!Pattern.matches("[0-9]+.[0-9]+", number)) {
				System.out.println("Invalid number. Try again: ");
				number = sc.nextLine();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return number;
	}
}
