package uebung_02;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

class BoundedBuffer<T> {
	private Queue<T> items;
	private int limit;
	public BoundedBuffer(int capacity){
		limit = capacity;
		items = new LinkedList<>();
	}

	public synchronized void put(T item) throws InterruptedException {
		while(items.size() >= limit){
			wait();
		}
		items.add(item);
		notifyAll();
	}

	public synchronized T get() throws InterruptedException {
		while(items.isEmpty()){
			wait();
		}
		T item = items.remove();
		notifyAll();
		return item;
	}
}

class Producer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Producer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		Random random = new Random();
		for (int i = 0; i < nofItems; i++) {
			try {
				buffer.put(random.nextLong());
			} catch (InterruptedException e) {
				System.out.println("Race condition error in Producer I guess");
				e.printStackTrace();
			}
		}
		System.out.println("Producer finished " + getName());
	}
}

class Consumer extends Thread {
	private final BoundedBuffer<Long> buffer;
	private final int nofItems;

	public Consumer(BoundedBuffer<Long> buffer, int nofItems) {
		this.buffer = buffer;
		this.nofItems = nofItems;
	}

	public void run() {
		for (int i = 0; i < nofItems; i++) {
			try {
				buffer.get();
			} catch (InterruptedException e) {
				System.out.println("Race conditions in Customer I guess");
				e.printStackTrace();
			}
		}
		System.out.println("Consumer finished " + getName());
	}
}

public class Simulation {
	private static final int NOF_PRODUCERS = 1;
	private static final int NOF_CONSUMERS = 10;
	private static final int BUFFER_CAPACITY = 1;
	// TOTAL_ELEMENTS must be a multiple of ELEMENTS_PER_PRODUCER and ELEMENTS_PER_CONSUMER
	private static final int TOTAL_ELEMENTS = 1000000; 
	private static final int ELEMENTS_PER_PRODUCER = TOTAL_ELEMENTS / NOF_PRODUCERS;
	private static final int ELEMENTS_PER_CONSUMER = TOTAL_ELEMENTS / NOF_CONSUMERS;

	public static void main(String[] args) throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		BoundedBuffer<Long> buffer = new BoundedBuffer<Long>(BUFFER_CAPACITY);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NOF_PRODUCERS; i++) {
			threads.add(new Producer(buffer, ELEMENTS_PER_PRODUCER));
		}
		for (int i = 0; i < NOF_CONSUMERS; i++) {
			threads.add(new Consumer(buffer, ELEMENTS_PER_CONSUMER));
		}
		for (Thread thread: threads) {
			thread.start();
		}
		for (Thread thread: threads) {
			thread.join();
		}
		long stopTime = System.currentTimeMillis();
		System.out.println("Producer-consumer simulation finished");
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
	}
}
