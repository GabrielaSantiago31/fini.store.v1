package validators;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Scanner;

import jakarta.mail.internet.ParseException;

public class BirthdateValidator {
	static Scanner sc = new Scanner(System.in);
	
	public static String birthdateValidator(String input) {
		try {
			while(isBirthdate(input) == false) {
				System.out.println("Invalid date. Try again [dd/MM/yyyy]: ");
				input = sc.nextLine().toUpperCase();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return input;
	}
	
	
	public static boolean isBirthdate(String bdate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    sdf.setLenient(false);
	    try {
	    	Date data = sdf.parse(bdate);
	    	return true;
	    }catch(java.text.ParseException e) {
	    	return false;
	    }
	}
}
