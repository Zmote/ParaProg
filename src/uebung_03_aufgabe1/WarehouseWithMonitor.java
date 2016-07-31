package uebung_03_aufgabe1;

public class WarehouseWithMonitor implements Warehouse {
	int stock = 0;
	int capacity;

	public WarehouseWithMonitor(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public synchronized void put(int amount) throws InterruptedException {
		while(stock + amount > capacity){
			wait();
		}
		stock += amount;
		notifyAll();
	}

	@Override
	public synchronized void get(int amount) throws InterruptedException {
		while(stock - amount < 0){
			wait();
		}
		stock -= amount;
		notifyAll();
	}
}
