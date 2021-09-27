package com.revature;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.diyORM.DIYORM;
import com.revature.models.Bank;
import com.revature.service.BankService;
import com.revature.util.Configuration;


// MAYBE ADD AN OPTION TO DELETE A TABLE 
public class DemoUI {
	
	static BankService bserv = new BankService();
	static Scanner scan = new Scanner(System.in);
	static DIYORM orm = DIYORM.getInstance();
	
	public static void main(String[] args) {
		menuStartup();
	}
	
	public static void menuStartup() {
		Configuration cfg = new Configuration();
		cfg.addAnnotatedClass(Bank.class);
		
		System.out.println("--------------------------------------------------"
				+ "\nWould you like to create a new table or use an existing one?"
				+ "\n1. Create a New Table" 
				+ "\n2. Use an Existing Table"
				+ "\n--------------------------------------------------");
		
		int tableInput= 0;
		boolean loop = false;
		while (!loop) {
			try {
				tableInput = scan.nextInt();
				if (tableInput >= 1 && tableInput <=2) {
					loop = true;
				} else {
					System.out.println("Please enter 1 or 2");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter 1 or 2");
				scan.next();
			}
		}
		
		if (tableInput == 1) {
			bserv.createTable(cfg);
			insertUser();
		} else if (tableInput == 2) {
			menu();
		}
	}
	
	
	public static void menu() {
		
		System.out.println("--------------------------------------------------"
				+ "\nWelcome! What would you like to do?"
				+ "\n1. Get User by Id"
				+ "\n2. Get all Users"
				+ "\n3. Insert a User into the Table"
				+ "\n4. Withdraw from a User"
				+ "\n5. Deposit to a User"
				+ "\n6. Delete a User"
				+ "\n7. Quit"
				+ "\n--------------------------------------------------");
		
		int input = 0;
		boolean menuInput = false;
		while (menuInput == false) {
			try {
				input = scan.nextInt();
				if (input >= 1 && input <=7) {
					menuInput = true;
				} else {
					System.out.println("Please enter 1-7");
				}
			} catch (InputMismatchException e) {
				System.out.println("Please enter 1-7");
				scan.next();
			}
		}
		
		// Get User by Id
		if (input == 1) {
			getUserById();
		}
		// Get all Users
		else if (input == 2) {
			getAllUsers();
		}
		// Insert a User into the Table
		else if (input == 3) {
			insertUser();
		}
		// Withdraw from a User
		else if (input == 4) {
			withdrawUser();
		}
		// Deposit to a User
		else if (input == 5) {
			depositUser();
		}
		// Delete a User
		else if (input == 6) {
			deleteUser();
		}
		// Quit
		else if (input == 7) {
			System.out.println("Closing");
			scan.close();
			System.exit(0);
		}
	}

	public static void getUserById() {
		
		System.out.println("Please enter an Id");
		int id = scan.nextInt();
		Bank user = new Bank(id);
		user = bserv.getUser(user);
		
		System.out.println(bserv.getUser(user).toString());
		menu();
	}

	public static void getAllUsers() {
		
		System.out.println(bserv.getAllUsers().toString());
		menu();
	}
	
	public static void insertUser() {
		
		System.out.println("Please enter a username");
		String username = scan.next();
		System.out.println("Please enter a password");
		String password = scan.next();
		System.out.println("Please enter an initial balance");
		double balance = scan.nextDouble();
		
		Bank user = new Bank(username, password, balance);
		
		bserv.insert(user);
		menu();
	}
	
	public static void withdrawUser() {
		
		System.out.println("Enter a user id");
		int id = scan.nextInt();
		System.out.println("How much would you like to withdraw?");
		double withdraw = scan.nextDouble();
		
		Bank user = new Bank(id);
		user = bserv.getUser(user);
		user.setBalance(user.getBalance() - withdraw);
		
		bserv.update(user);
		menu();
	}
	
	public static void depositUser() {
		
		System.out.println("Enter a user id");
		int id = scan.nextInt();
		System.out.println("How much would you like to deposit?");
		double deposit= scan.nextDouble();
		
		Bank user = new Bank(id);
		user = bserv.getUser(user);
		user.setBalance(user.getBalance() + deposit);
		
		bserv.update(user);
		menu();
	}
	
	public static void deleteUser() {
		
		System.out.println("Enter a user id");
		int id = scan.nextInt();
		Bank user = new Bank(id);
		
		bserv.delete(user);
		menu();
	}
}
