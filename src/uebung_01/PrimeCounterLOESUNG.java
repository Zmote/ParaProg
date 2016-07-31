package uebung_01;

import java.util.ArrayList;
import java.util.Collection;

public class PrimeCounterLOESUNG {
	private static boolean isPrime(long number) {
		for (long factor = 2; factor * factor <= number; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private static long countPrimes(long start, long end) {
		long count = 0;
		for (long number = start; number < end; number++) {
			if (isPrime(number)) {
				count++;
			}
		}
		return count;
	}

	private static final long START = 1_000_000L;
	private static final long END = 10_000_000L;

	private static final long NUM_THREADS = Runtime.getRuntime().availableProcessors();

	private static class CountingThread extends Thread {

		private final long start;
		private final long end;
		private long count;

		public long getCount() {
			return count;
		}

		public CountingThread(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			count = countPrimes(start, end);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();

		Collection<CountingThread> threads = new ArrayList<>();

		final long range = (END - START) / NUM_THREADS;

		for (int i = 0; i < NUM_THREADS; i++) {
			long start = START + i * range;
			long end = start + range;

			System.out.printf("Starting thread %d to count primes in the range [%d, %d)\n",
					i, start, end);

			CountingThread thread = new CountingThread(start, end);

			threads.add(thread);
			thread.start();
		}

		long count = 0;

		for (CountingThread thread : threads) {
			thread.join();
			count += thread.getCount();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("#Primes: " + count + " Time: "
				+ (endTime - startTime) + " ms");
	}
}
