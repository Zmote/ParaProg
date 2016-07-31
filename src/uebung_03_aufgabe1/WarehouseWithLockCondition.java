package uebung_03_aufgabe1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WarehouseWithLockCondition implements Warehouse {
	int stock = 0;
	int capacity;
	private Lock monitor;
	private Condition nonFull;
	private Condition nonEmpty;

	public WarehouseWithLockCondition(int capacity, boolean fair) {
		this.capacity = capacity;
		monitor = new ReentrantLock(fair);
		nonFull = monitor.newCondition();
		nonEmpty = monitor.newCondition();
	}

	@Override
	public void put(int amount) throws InterruptedException {
		monitor.lock();
		try{
			while(stock + amount > capacity){
				nonFull.await();
			}
			stock += amount;
			nonEmpty.signal();
		}finally{
			monitor.unlock();
		}
	}

	@Override
	public void get(int amount) throws InterruptedException {
		monitor.lock();
		try{
			while(stock - amount < 0){nonEmpty.await();};
			stock -= amount;
			nonFull.signal();
		}finally{
			monitor.unlock();
		}
	}
}
