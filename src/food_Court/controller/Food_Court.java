package food_Court.controller;

import java.util.Scanner;

import food_Court.dao.User_Registration;
import food_Court.dto.User;

public class Food_Court {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		User_Registration ur = new User_Registration();
		ur.createUserTable();

		System.out.println("Welcome to JSpider's Food Court");
		System.out.println("-------------------------------");

		boolean check = true;
		do {
			System.out.println(
					"\nPlease Enter -> 1 For Registration (Offer New User gets Wallet Balance of Rs 300 for Free)\n\t     -> 2 For Login\n\t     -> 3 For Exit");
			int choice = sc.nextInt();
			switch (choice) {
			case 1: {
				System.out.print("Please Enter your ID = ");
				int id = sc.nextInt();
				System.out.print("Enter your Name = ");
				String name = sc.next();
				System.out.print("Enter a Good Password = ");
				String password = sc.next();
				System.out.print("Enter your E-Mail = ");
				String email = sc.next();
				double wallet = 300.0;
				System.out.print("Select your Gender -> 1 For Male OR 2 For Female = ");
				String gender = sc.next();
				if (gender.equals("1"))
					gender = "Male";
				else if (gender.equals("2"))
					gender = "Female";
				else
					gender = "LGBTQ";
				System.out.println("\t\t\t" + gender);
				System.out.print("Enter your Phone Number = ");
				long phone = sc.nextLong();

				User user = new User(id, name, password, email, wallet, gender, phone);
				System.out.println("Your Details = " + user);
				ur.insertUserDetail(user);
			}
				break;
			case 2: {
				System.out.print("Enter Your Name = ");
				String name = sc.next();
				if (ur.nameExist(name)) {
					System.out.print("Enter your Password = ");
					String password = sc.next();
					if (ur.userLogin(name, password)) {
						ur.displayFoodMenu();
						System.out.print("\nEnter the Id of the Dish You want to Eat = ");
						int dishId = sc.nextInt();
						if(!ur.transaction(name, dishId)) {
							System.out.print("Enter 1 to Add Money or any Number to Exit = ");
							if(sc.nextInt() == 1) {
								System.out.print("Enter the Amount to be Added = ");
								double money = sc.nextDouble();
								ur.updateWallet(name, money);
							}
						}
					}
				} else {
					System.out.println("\nThe Specified Name does Not Exist. Login Aborted.\n");
				}
			}
				break;
			case 3:
				check = false;
				System.out.println("\nExited Successfully");
				break;
			default:
				System.out.println("\nPlease Enter 1, 2 or 3 only");
				break;
			}
		} while (check);
	}

}
