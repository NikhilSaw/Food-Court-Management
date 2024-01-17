package food_Court.dao;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

import com.mysql.cj.jdbc.Driver;

import food_Court.dto.User;

public class User_Registration {

	private Connection getConnection() throws Exception {
		DriverManager.registerDriver(new Driver());
		FileInputStream fis = new FileInputStream("dbconfig.properties");
		Properties p = new Properties();
		p.load(fis);
		Connection c = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/Food_Court?createDatabaseIfNotExist=true", p);
		return c;
	}

	public void createUserTable() throws Exception {
		Connection c = getConnection();
		Statement s = c.createStatement();
		String createTable = "create table if not exists User (ID int primary key, Name varchar(45) not null, Password varchar(45) not null, E_Mail varchar(45) not null, Wallet double not null, Gender varchar(45) not null, Phone_No bigint not null)";
		s.execute(createTable);
		s.close();
		c.close();
	}

	public void displayFoodMenu() throws Exception {
		Connection c = getConnection();
		System.out.println("\nFood Menu:\n");
		String selectQuery = "SELECT * FROM Food_Menu";
		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(selectQuery);

		System.out.println("+-------+-------------------------------------+------------+");
		System.out.printf("| %-5s | %-35s | %-10s |\n", "Id", "Name", "Price");
		System.out.println("+-------+-------------------------------------+------------+");

		while (rs.next()) {
			int id = rs.getInt("ID");
			String name = rs.getString("Name");
			double price = rs.getDouble("Price");
			System.out.printf("| %-5d | %-35s | %-10.2f |\n", id, name, price);
		}
		System.out.println("+-------+-------------------------------------+------------+");
		s.close();
		c.close();
	}

	public void insertUserDetail(User user) throws Exception {
		Connection c = getConnection();
		String insert = "insert into User values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = c.prepareStatement(insert);
		ps.setInt(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getEmail());
		ps.setDouble(5, user.getWallet());
		ps.setString(6, user.getGender());
		ps.setLong(7, user.getPhone());
		ps.execute();
		ps.close();
		c.close();
		System.out.println("\nInserted Successfully\n");
	}

	public boolean nameExist(String name) throws Exception {
		Connection c = getConnection();
		String checkName = "SELECT COUNT(*) AS count FROM User WHERE Name = ?";
		PreparedStatement ps = c.prepareStatement(checkName);
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			int count = rs.getInt("count");
			return count > 0;
		}
		return false;
	}

	public boolean userLogin(String name, String password) throws Exception {
		Connection c = getConnection();
		String loginQuery = "SELECT * FROM User WHERE Name = ? AND Password = ?";
		PreparedStatement ps = c.prepareStatement(loginQuery);
		ps.setString(1, name);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			System.out.println("\nLogin successful!\n");
			return true;
		} else {
			System.out.println("\nInvalid Credentials. Login Failed.\n");
			return false;
		}
	}

	public boolean transaction(String name, int dishId) throws Exception {
		Connection c = getConnection();
		String dish = "SELECT Price FROM Food_Menu WHERE ID = ?";
		PreparedStatement ps = c.prepareStatement(dish);
		ps.setInt(1, dishId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			double price = rs.getDouble("Price");
			System.out.println("\nDish Price: " + price);
			String getUserWallet = "SELECT wallet FROM User WHERE Name = ?";
			double userWallet = 0.0;
			PreparedStatement psn = c.prepareStatement(getUserWallet);
			psn.setString(1, name);
			ResultSet rsw = psn.executeQuery();
			if (rsw.next()) {
				userWallet = rsw.getDouble("wallet");
				if (userWallet > price) {
					double newWalletBalance = userWallet - price;
					String updateWallet = "UPDATE User SET Wallet = ? WHERE Name = ?";
					PreparedStatement updateWalletPs = c.prepareStatement(updateWallet);
					updateWalletPs.setDouble(1, newWalletBalance);
					updateWalletPs.setString(2, name);
					updateWalletPs.executeUpdate();
					System.out.println("Transaction successful. Updated wallet balance: " + newWalletBalance);
					return true;
				} else
					System.out.println("Insufficient funds. Transaction failed.");
				return false;
			}
		} else
			System.out.println("Dish not found.");
		return false;
	}

	public void updateWallet(String name, double money) throws Exception {
		Connection c = getConnection();
		String getUserWallet = "SELECT wallet FROM User WHERE Name = ?";
		double userWallet = 0.0;
		PreparedStatement psn = c.prepareStatement(getUserWallet);
		psn.setString(1, name);
		ResultSet rsw = psn.executeQuery();
		if (rsw.next()) {
			userWallet = rsw.getDouble("wallet");
			double newWalletBalance = userWallet + money;
			String updateWallet = "UPDATE User SET Wallet = ? WHERE Name = ?";
			PreparedStatement updateWalletPs = c.prepareStatement(updateWallet);
			updateWalletPs.setDouble(1, newWalletBalance);
			updateWalletPs.setString(2, name);
			updateWalletPs.executeUpdate();
			System.out.println("Transaction successful. Updated wallet balance: " + newWalletBalance);
		}
	}
}