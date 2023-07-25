package validators;

import java.util.Scanner;
import java.util.regex.Pattern;

public class PhoneValidator {
	static Scanner sc = new Scanner(System.in);
	
	public static String phoneValidator(String input) {
		try {
			while(isTelephone(input) == false) {
				System.out.println("Invalid telephone number. Do not forget the DDD and try again: ");
				input = sc.nextLine().toUpperCase();
			}
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		
		return input;
	}
	
	
	private static boolean isTelephone(String telephone) {
		telephone = telephone.replaceAll("\\D", "");
		if(!(telephone.length()>=10 && telephone.length()<=11)) {
			return false;
		}
		if(telephone.length() == 11 && Integer.parseInt(telephone.substring(2, 3))!= 9) {
			return false;
		}
		
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(telephone.charAt(0)+"{"+telephone.length()+"}");
	    java.util.regex.Matcher m = p.matcher(telephone);
	    if(m.find()) return false;
	    
	    Integer[] codigosDDD = {
	            11, 12, 13, 14, 15, 16, 17, 18, 19,
	            21, 22, 24, 27, 28, 31, 32, 33, 34,
	            35, 37, 38, 41, 42, 43, 44, 45, 46,
	            47, 48, 49, 51, 53, 54, 55, 61, 62,
	            64, 63, 65, 66, 67, 68, 69, 71, 73,
	            74, 75, 77, 79, 81, 82, 83, 84, 85,
	            86, 87, 88, 89, 91, 92, 93, 94, 95,
	            96, 97, 98, 99};
	    
	    if ( java.util.Arrays.asList(codigosDDD).indexOf(Integer.parseInt(telephone.substring(0, 2))) == -1) return false;
	    
	    Integer[] prefixos = {2, 3, 4, 5, 7};
	    
	    if (telephone.length() == 10 && java.util.Arrays.asList(prefixos).indexOf(Integer.parseInt(telephone.substring(2, 3))) == -1) return false;
	    
	    return true;
	}
}
