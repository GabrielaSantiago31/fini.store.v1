package models;

public class User {
	
	private int id;
	private String name;
	private String lastName;
	private String email;
	private String phone;
	private String cpf;
	private String birthdate;
	private Address address;
	private String password;
	
	public User(String name, String lastName,String email, String phone, String cpf, String birthdate, Address address,
			String password){
		super();
		
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.cpf = cpf;
		this.birthdate = birthdate;
		this.address = address;
		this.password = password;
	}
	
	public User() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name){
			this.name = name;	
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "" + name + ";" + lastName + ";" + email + ";" + phone + ";" + cpf
				+ ";" + birthdate + ";" + address + ";" + password + "\n";
	}
		
	
}
