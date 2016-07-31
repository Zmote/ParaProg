//Aufgabe 2a
//package uebung_02;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//class BankAccount3 {
//	private int balance = 0;
//
//	public synchronized void deposit(int amount) {
//		balance += amount;
//		notifyAll();
//	}
//
//	public synchronized void withdraw(int amount) throws InterruptedException{
//		while(amount > balance){
//			wait();
//		}
//		balance -= amount;
//	}
//
//	public synchronized int getBalance() {
//		return balance;
//	}
//}
//
//class BankCreditCustomer extends Thread {
//	private static final int NOF_TRANSACTIONS = 100;
//	private final BankAccount3 account;
//	private final int amount;
//
//	public BankCreditCustomer(BankAccount3 account, int amount) {
//		this.account = account;
//		this.amount = amount;
//	}
//	
//	//Lösung mit Thread.yield() funktioniert nicht, da damit nur 
//	//der aktuelle Thread freigegeben wird, aber es wird nicht 
//	//sichergestellt, dass der kritische Bereich nicht verletzt wird
//	@Override
//	public void run() {
//		for (int i = 0; i < NOF_TRANSACTIONS; i++) {
//			try{
//			account.withdraw(amount);
//			System.out.println("Use credit " + amount + " by " + Thread.currentThread().getName());
//			account.deposit(amount);
//			}catch(InterruptedException ex){
//				ex.printStackTrace();
//				throw new AssertionError();
//			}
//		}
//	}
//}
//
//public class BankTest2 {
//	private static final int NOF_CUSTOMERS = 10;
//	private static final int START_BUDGET = 1000;
//
//	public static void main(String[] args) throws InterruptedException {
//		BankAccount3 account = new BankAccount3();
//		List<BankCreditCustomer> customers = new ArrayList<>();
//		Random random = new Random(4711);
//		for (int i = 0; i < NOF_CUSTOMERS; i++) {
//			customers.add(new BankCreditCustomer(account, random.nextInt(1000)));
//		}
//		for (BankCreditCustomer customer: customers) {
//			customer.start();
//		}
//		account.deposit(START_BUDGET);
//		for (BankCreditCustomer customer: customers) {
//			customer.join();
//		}
//		
//		if(account.getBalance() != START_BUDGET){
//			throw new AssertionError("Incorrect final balance: " + account.getBalance());
//		}
//	}
//}

package uebung_02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class BankAccount3 {
	private int balance = 0;

	public synchronized void deposit(int amount) {
		balance += amount;
		notifyAll();
	}

	public synchronized void withdraw(int amount, int timeMillis) throws InterruptedException{
		if(timeMillis < 0){
			throw new IllegalArgumentException("timeMillis is negative");
		}
		long startTime = System.currentTimeMillis();
		long currentTime = startTime;
		//da currentTime in der while-Schleife immer auf die "current" Time gesetzt wird,
		//wird timeMillis nach der angegeben Zeit erreicht, ie. CountDown Mechanismus ist somit umgesetzt
		while(amount > balance && currentTime - startTime < timeMillis){
			wait(timeMillis - currentTime + startTime);
			currentTime = System.currentTimeMillis();
		}
		balance -= amount;
	}

	public synchronized int getBalance() {
		return balance;
	}
}

class BankCreditCustomer extends Thread {
	private static final int NOF_TRANSACTIONS = 100;
	private final BankAccount3 account;
	private final int amount;

	public BankCreditCustomer(BankAccount3 account, int amount) {
		this.account = account;
		this.amount = amount;
	}
	
	//Lösung mit Thread.yield() funktioniert nicht, da damit nur 
	//der aktuelle Thread freigegeben wird, aber es wird nicht 
	//sichergestellt, dass der kritische Bereich nicht verletzt wird
	@Override
	public void run() {
		for (int i = 0; i < NOF_TRANSACTIONS; i++) {
			try{
			account.withdraw(amount, 10000);
			System.out.println("Use credit " + amount + " by " + Thread.currentThread().getName());
			account.deposit(amount);
			}catch(InterruptedException ex){
				ex.printStackTrace();
				throw new AssertionError();
			}
		}
	}
}

public class BankTest2 {
	private static final int NOF_CUSTOMERS = 10;
	private static final int START_BUDGET = 1000;

	public static void main(String[] args) throws InterruptedException {
		BankAccount3 account = new BankAccount3();
		List<BankCreditCustomer> customers = new ArrayList<>();
		Random random = new Random(4711);
		for (int i = 0; i < NOF_CUSTOMERS; i++) {
			customers.add(new BankCreditCustomer(account, random.nextInt(1000)));
		}
		for (BankCreditCustomer customer: customers) {
			customer.start();
		}
		account.deposit(START_BUDGET);
		for (BankCreditCustomer customer: customers) {
			customer.join();
		}
		
		if(account.getBalance() != START_BUDGET){
			throw new AssertionError("Incorrect final balance: " + account.getBalance());
		}
	}
}

