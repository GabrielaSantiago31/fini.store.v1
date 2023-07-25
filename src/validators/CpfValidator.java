package validators;

import java.util.InputMismatchException;
import java.util.Scanner;

import dao.UsersDao;
import models.User;

public class CpfValidator {
	static Scanner sc = new Scanner(System.in);
	
	public static String cpfValidator(String input) {
		try {
			while(isCPF(input) == false || isCpfAlreadyRegistered(input)) {
				System.out.println("Invalid CPF. Type only numbers and try again: ");
				input = sc.nextLine();
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public static boolean isCpfAlreadyRegistered(String cpf) {
		for(User u : UsersDao.listOfUsers()) {
			if(u.getCpf().equals(cpf)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isCPF(String CPF) {
		if(CPF.equals("00000000000")||
		CPF.equals("11111111111")||
		CPF.equals("22222222222")||CPF.equals("33333333333")||
		CPF.equals("44444444444")||CPF.equals("55555555555")||
		CPF.equals("66666666666")||CPF.equals("77777777777")||
		CPF.equals("88888888888")||CPF.equals("99999999999")||
		(CPF.length() != 11)) {
			return false;
		}
		
		char dig10, dig11;
		int sm, i, r, num, peso;
		
		try {
			sm = 0;
			peso = 10;
			
			for(i=0; i<9; i++) {
				num = (int)(CPF.charAt(i)-48);
				sm = sm + (num*peso);
				peso = peso -1;
			}
			r = 11 - (sm % 11);
			
			if((r==10)||(r==11)) {
				dig10 = '0';
			}else {
				dig10 = (char)(r + 48);
			}
			
			sm = 0;
			peso = 11;
			
			for(i = 0; i <10; i++) {
				num = (int)(CPF.charAt(i)-48);
				sm = sm + (num * peso);
				peso = peso -1;
			}
			
			r = 11 - (sm % 11);
			
			if((r==10)||(r==11)) {
				dig11 = '0';
			}else {
				dig11 = (char)(r + 48);
			}
			
			if((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
				return true;
			}else {
				return false;
			}
			
		}catch(InputMismatchException error) {
			return false;
		}
	}
	
}
