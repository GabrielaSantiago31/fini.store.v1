package view;


import java.util.Scanner;
import dao.AddressesDao;
import dao.ProductsDao;
import dao.UsersDao;
import models.User;
import validators.EmailValidator;
import validators.IntegerValidator;
import validators.PasswordValidator;


public class View {
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		StoreViewMethods view = new StoreViewMethods();
		
		int start = 0;
		String email = "";
		String password = "";
		
		view.signUpAndLogin();
		
		int option = Integer.parseInt(IntegerValidator.integerValidator(scanner.nextLine()));
		
		while(start == 0) {
			
			if(option == 1) {
				
				System.out.println(view.registerAnUser(true));
				
				System.out.println("Do you want to log in?[Y/N] ");
				String answer = scanner.nextLine().toUpperCase();
				if(answer.equals("Y")) {
					option = 2;
				}else {
					System.out.println("Come back anytime!");
					start = 1;
				}
			}
			
			if(option == 2) {
				System.out.println("Login: ");
				email = EmailValidator.emailValidator(scanner.nextLine().toUpperCase());
				System.out.println("Password: ");
				password = PasswordValidator.passwordValidator(scanner.nextLine());   
				
				User u = UsersDao.returnAnUserByEmail(email, password);
				
				if(u == null) {
					System.out.println("User not found");
					System.out.println("Do you want to sign up?[Y/N] ");
					String answer = scanner.nextLine().toUpperCase();
					if(answer.equals("Y")) {
						option = 1;
					}else {
						System.out.println("Come back anytime!");
						start = 1;
					}
				}else if(u.getEmail().equals("PX47069@GMAIL.COM") && u.getPassword().equals("@Admin11")) {
					option = 4;
				}else {
					option = 3;
				}
			}
			
			if(option == 3) {
				
				view.showOptionsToRegisteredUser();
				
				int answer = Integer.parseInt(IntegerValidator.integerValidator(scanner.nextLine()));
				
				switch(answer) {
				case 1:
					
					ProductsDao.printCatalog();
					break;
					
				case 2:
					view.registerAnOrder(email, password);
					
					break;
					
				case 3:
					
					User u = UsersDao.returnAnUserByEmail(email, password);
					User userUpdated = view.registerAnUser(false);
					
					userUpdated.getAddress().setId(u.getAddress().getId());
					AddressesDao.update(userUpdated.getAddress());
					userUpdated.setId(u.getId());
					UsersDao.update(userUpdated);
					break;
					
				case 4:
					
					start = 1;
					System.out.println("Come back anytime!");
					break;
				}
			}
			
			if(option == 4) {
				view.showOptionsToAdmin();
				int answer = Integer.parseInt(IntegerValidator.integerValidator( scanner.nextLine()));
				switch(answer) {
				case 1:
					
					ProductsDao.printCatalog();
					break;
					
				case 2:
					
					view.registerProductIngredients(view.registerAProduct().getId());
					
					System.out.println("Product successfully registered!\n");
					break;
					
				case 3:
					
					view.registerAnIngredient();
					
					break;
					
				case 4:
					
					User u = UsersDao.returnAnUserByEmail(email, password);
					User userUpdated = view.registerAnUser(false);
					
					userUpdated.getAddress().setId(u.getAddress().getId());
					AddressesDao.update(userUpdated.getAddress());
					userUpdated.setId(u.getId());
					UsersDao.update(userUpdated);
					break;
					
				case 5:
					
					start = 1;
					System.out.println("Come back anytime!");
					break;
					
					}
				}
			}	
		}
}
