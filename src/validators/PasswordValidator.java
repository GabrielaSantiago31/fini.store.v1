package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PasswordValidator {
	static Scanner sc = new Scanner(System.in);
	
	public static String passwordValidator(String input) {
		try {
			while(!Pattern.matches("^(?:(?=.*\\\\d)(?=.*[A-Z])(?=.*[a-z])|" +
					"(?=.*\\d)(?=.*[^A-Za-z0-9])(?=.*[a-z])|" +
					"(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])|" +
					"(?=.*\\d)(?=.*[A-Z])(?=.*[^A-Za-z0-9]))(?!.*(.)\\1{2,})" +
					"[A-Za-z0-9!~<>,;:_=?*+#.\"&§%°()\\|\\[\\]\\-\\$\\^\\@\\/]" +
					"{8,32}$", input)) {
				
				System.out.println("Invalid password. Try again: ");
				
				System.out.println("[*Must have at least one numeric character]\n"
						+ "[*Must have at least one lowercase character]\n"
						+ "[*Must have at least one uppercase character]\n"
						+ "[*Must have at least one special symbol among @#$%]\n"
						+ "[*Character repetition is not allowed\n"
						+ "[*Password length should be between 8 and 32]");
				
				input = sc.nextLine();
			}
			
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return input;
	}
}
