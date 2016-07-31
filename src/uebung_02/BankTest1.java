//Aufgabe 1a
//package uebung_02;
// 
//import java.util.ArrayList;
//import java.util.List;
//
//class BankAccount {
//	private int balance = 0;
//
//	public void deposit(int amount){
//		balance += amount;
//	}
//
//	public boolean withdraw(int amount){
//		if (amount <= balance) {
//			balance -= amount;
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	public int getBalance() {
//		return balance;
//	}
//}
//
//class BankCustomer extends Thread {
//	private static final int NOF_TRANSACTIONS = 10000000;
//	private final BankAccount account;
//	
//	public BankCustomer(BankAccount account) {
//		this.account = account;
//	}
//	
//	@Override
//	public void run() {
//		for (int k = 0; k < NOF_TRANSACTIONS; k++) {
//				account.deposit(100);
//				account.withdraw(100);
//		}
//	}
//}
//
//public class BankTest1 {
//	private static final int NOF_CUSTOMERS = 10;
//
//	public static void main(String[] args) throws InterruptedException {
//		BankAccount account = new BankAccount();
//		List<BankCustomer> customers = new ArrayList<>();
//		for (int i = 0; i < NOF_CUSTOMERS; i++) {
//			customers.add(new BankCustomer(account));
//		}
//		for(BankCustomer customer: customers){
//			customer.start();
//		}
//		
//		for(BankCustomer customer: customers){
//			customer.join();
//		}
//		
//		if(account.getBalance() !=0){
//			throw new AssertionError("Inconsistency due to race condition. Final balance: " + account.getBalance());
//		}
//	}
//}

//Aufgabe 1b
package uebung_02;

import java.util.ArrayList;
import java.util.List;

class BankAccount {
	private int balance = 0;

	public synchronized void deposit(int amount){
		balance += amount;
	}

	public synchronized boolean withdraw(int amount){
		if (amount <= balance) {
			balance -= amount;
			return true;
		} else {
			return false;
		}
	}

	public int getBalance() {
		return balance;
	}
}

class BankCustomer extends Thread {
	private static final int NOF_TRANSACTIONS = 10000000;
	private final BankAccount account;
	public BankCustomer(BankAccount account) {
		this.account = account;
	}
	
	@Override
	public void run() {
		for (int k = 0; k < NOF_TRANSACTIONS; k++) {
			account.deposit(100);
			boolean success = account.withdraw(100);
			if(!success){
				throw new AssertionError("Inconsistency due to race condition");
			}
		}
	}
}

public class BankTest1 {
	private static final int NOF_CUSTOMERS = 10;

	public static void main(String[] args) throws InterruptedException {
		BankAccount account = new BankAccount();
		List<BankCustomer> customers = new ArrayList<>();
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			customers.add(new BankCustomer(account));
		}
		for(BankCustomer customer: customers){
			customer.start();
		}
		
		for(BankCustomer customer: customers){
			customer.join();
		}
		
		if(account.getBalance() !=0){
			throw new AssertionError("Inconsistency due to race condition. Final balance: " + account.getBalance());
		}
	}
}
