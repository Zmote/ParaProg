package uebung_01;

public class MaxThreadCount {
	public static long threadCount = 0;
	public static void main(String[] args) {
		while(true){
			threadCount++;
			new MaxCountThread().start();
			System.out.println(threadCount);
		}
	}
}

class MaxCountThread extends Thread {
	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(10000);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
