package view;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dao.AddressesDao;
import dao.IngredientsDao;
import dao.OrdersDao;
import dao.ProductsDao;
import dao.UsersDao;
import mail.EmailSender;
import models.Address;
import models.Ingredient;
import models.Order;
import models.Product;
import models.User;
import validators.ZipCodeValidator;
import validators.BirthdateValidator;
import validators.CpfValidator;
import validators.DoubleValidator;
import validators.EmailValidator;
import validators.NameValidator;
import validators.IntegerValidator;
import validators.PasswordValidator;
import validators.PhoneValidator;
import validators.ProductNumberValidator;

public class StoreViewMethods {
	
		public void signUpAndLogin() {
			System.out.println("================================");
			System.out.println("           FINI store           ");
			System.out.println("================================");
			
			System.out.println("================================");
			System.out.println("Choose an option: ");
			System.out.println("[1] Sign up");
			System.out.println("[2] Log in");
			System.out.println("================================");
		}
	
		public void showOptionsToRegisteredUser() {
			System.out.println("Choose an option: ");
			System.out.println("[1]Product catalog");
			System.out.println("[2]Order");
			System.out.println("[3]Update user");
			System.out.println("[4]Sign out");
		}
		
		public void showOptionsToAdmin() {
			System.out.println("Choose an option: ");
			System.out.println("[1]Product catalog");
			System.out.println("[2]Register products");
			System.out.println("[3]Register ingredients");
			System.out.println("[4]Update admin");
			System.out.println("[5]Sign out");
		}
		
		public User registerAnUser(boolean validateCpf) {
			Scanner sc = new Scanner(System.in);
			User user = new User();
			
			System.out.println("============= Personal Information =============");
			System.out.println("Name: ");
			user.setName(NameValidator.nameValidator(sc.nextLine()).toUpperCase()); 
			System.out.println("Last name: ");
			user.setLastName(NameValidator.nameValidator(sc.nextLine()).toUpperCase()); 
			System.out.println("Email: ");
			user.setEmail(EmailValidator.emailValidator(sc.nextLine()).toUpperCase());
			System.out.println("Cell phone: ");
			user.setPhone(PhoneValidator.phoneValidator(sc.nextLine()).toUpperCase());
			if(validateCpf) {
				System.out.println("CPF: ");
				user.setCpf(CpfValidator.cpfValidator(sc.nextLine()).toUpperCase());
			}
			System.out.println("Birthdate: ");
			user.setBirthdate(BirthdateValidator.birthdateValidator(sc.nextLine()).toUpperCase());
			System.out.println("Password: ");
			user.setPassword(PasswordValidator.passwordValidator(sc.nextLine()));
			
			user.setAddress(registerAnAddress());
			
			if(validateCpf) {
				AddressesDao.addressesValidation(user.getAddress());
				UsersDao.add(user);
					
				System.out.println("Successful registration!");
			}
			
			
			return user;
		}
		
		public Address registerAnAddress() {
			Scanner sc = new Scanner(System.in);
			Address address;
			
			System.out.println("=================== Address ===================");
			System.out.println("Number: ");
			String number = IntegerValidator.integerValidator(sc.nextLine());
			System.out.println("Zip Code: ");
			String zipCode = ZipCodeValidator.zipCodeValidator(sc.nextLine());
			
			address = ZipCodeValidator.generateAddressByZipCode(zipCode);
			address.setNumero(number);
			
			return address;
		}
		
		public void registerProductIngredients(int productId) {
			Scanner sc = new Scanner(System.in);
			
			System.out.println("======== Product Ingredients =======");
			String addIngredient = "Y";
			while(addIngredient.equals("Y")) {
				System.out.println("Ingredient: ");
				String ingredient = NameValidator.nameValidator(sc.nextLine()).toUpperCase();
				System.out.println("Quantity (per package): ");
				int ingQuantity = Integer.parseInt(IntegerValidator.integerValidator(sc.nextLine()));
				ProductsDao.addProductIngredients(productId, ingredient, ingQuantity);
				System.out.println("Another Ingredient? [Y/N] ");
				addIngredient = sc.nextLine().toUpperCase();
			}
		}
		
		
		public Product registerAProduct() {
			Scanner sc = new Scanner(System.in);
			System.out.println("Name: ");
			String name = NameValidator.nameValidator(sc.nextLine()).toUpperCase();
			System.out.println("Flavor: ");
			String flavor = NameValidator.nameValidator(sc.nextLine()).toUpperCase();
			System.out.println("Price: ");
			double price = Double.parseDouble(DoubleValidator.doubleValidator(sc.nextLine()));
			System.out.println("Grams: ");
			int gramsPerPackage = Integer.parseInt(IntegerValidator.integerValidator(sc.nextLine()));
			System.out.println("Category: ");
			String category = NameValidator.nameValidator(sc.nextLine()).toUpperCase();
			
			Product product = new Product(name,flavor,price,gramsPerPackage, category);
			ProductsDao.add(product);
			return product;
		}
		
		public void registerAnIngredient() {
			Scanner sc = new Scanner(System.in);
			String resp = "Y";
			while(resp.equals("Y")) {
				System.out.println("Name: ");
				String nameIngredient = NameValidator.nameValidator(sc.nextLine()).toUpperCase();
				System.out.println("Quantity: ");
				int quantity = Integer.parseInt(IntegerValidator.integerValidator(sc.nextLine())); 
				
				Ingredient ingredient = new Ingredient(nameIngredient, quantity);
				IngredientsDao.add(ingredient);
				System.out.println("Ingredient successfully registered!\n");
				System.out.println("Do you want to register another ingredient? [Y/N] ");
				resp = sc.nextLine().toUpperCase();
			}
		}
		
		public void registerAnOrder(String email, String password) {
			Scanner sc = new Scanner(System.in);
			Map<Product, Integer> productsIdAndQuantities = new HashMap<>();
			
			String clientAnswer = "Y";
			while(clientAnswer.equals("Y")) {
				System.out.println("Product number: ");
				int productId = Integer.parseInt(ProductNumberValidator.productNumberValidator(sc.nextLine())); 
				System.out.println("Quantity: ");
				int productQuantity = Integer.parseInt((IntegerValidator.integerValidator(sc.nextLine())));
				
				if(ProductsDao.isProductAvailable(productId)== false) {
					System.out.println("Product Unavailable!");
					
				}else if(ProductsDao.isProductQuantityAvailable(productId,productQuantity)== false){
					System.out.println("Quantity Unavailable!");
					
				}else{
					productsIdAndQuantities.put(ProductsDao.returnAProductById(productId), productQuantity);
					ProductsDao.updateProductsIngredientsQuantity(productId, productQuantity);
				}
				System.out.println("Do you want to add another product?[Y/N]");
				clientAnswer = sc.nextLine().toUpperCase();
			}
			
			if(productsIdAndQuantities.size() != 0) {
				Order order = new Order(productsIdAndQuantities, UsersDao.returnAnUserByEmail(email, password),LocalDateTime.now());
				OrdersDao.add(order);
				
				EmailSender sender = new EmailSender(email, UsersDao.returnAnUserByEmail(email, password).getName(),order.getId());
				sender.sendAnEmail();
			}
		}
	
}
