package food_Court.dto;

public class User {

	private int id;
	private String name;
	private String password;
	private String email;
	private double wallet;
	private String gender;
	private long phone;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", wallet="
				+ wallet + ", gender=" + gender + ", phone=" + phone + "]";
	}

	public User() {
		super();
	}

	public User(int id, String name, String password, String email, double wallet, String gender, long phone) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.wallet = wallet;
		this.gender = gender;
		this.phone = phone;
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

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getWallet() {
		return wallet;
	}

	public void setWallet(double wallet) {
		this.wallet = wallet;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

}
