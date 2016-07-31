package uebung_01;

import java.util.Scanner;

public class ConsoleTicker {
//	private static void periodTicker(char sign, int intervallMillis) 
//			throws InterruptedException {
//		while (true) {
//			System.out.print(sign);
//			Thread.sleep(intervallMillis);
//		}
//	}

	public static void main(String[] args) throws InterruptedException {
		//TODO: Formatting, every thread on sepearte line
		char sign = 'o';
		int intervallMillis = 40;
		MyThread t1 = new MyThread('.',10);
		MyThread t2 = new MyThread('*',20);
		MyRunnable r1 = new MyRunnable('/',30);
		Thread t3 = new Thread(r1);
		Thread t4 = new Thread(()-> 
		{
			while (true) {
			System.out.print(sign);
			try {
				Thread.sleep(intervallMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		});
		Thread t5 = new Thread(()->{
			String input = null;
			while(input == null){
			Scanner scanner = new Scanner(System.in);
			input = scanner.next();
			scanner.close();
			};
		});
		t1.setDaemon(true);
		t2.setDaemon(true);
		t3.setDaemon(true);
		t4.setDaemon(true);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}

class MyThread extends Thread{
	private char sign;
	private int intervallMillis;
	
	public MyThread(char sign,int intervallMillis) {
		this.sign = sign;
		this.intervallMillis = intervallMillis;
	}
	
	@Override
	public void run(){
		while (true) {
			System.out.print(sign);
			try {
				Thread.sleep(intervallMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

class MyRunnable implements Runnable{
	private char sign;
	private int intervallMillis;

	public MyRunnable(char sign, int intervallMillis){
		this.sign = sign;
		this.intervallMillis = intervallMillis;
		
	}

	@Override
	public void run() {
		while (true) {
			System.out.print(sign);
			try {
				Thread.sleep(intervallMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
