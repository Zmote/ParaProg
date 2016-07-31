package uebung_01;

public class PrimeCounter {
	private static long total = 0;
	private static boolean isPrime(long number) {
		for (long factor = 2; factor * factor <= number; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}
	
	//Siehe Lösung für korrkete Vorgehensweise --> Arbeit mit Thread-Klassen
	//range wird als Konstruktor-Argument übergeben

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

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		long count = withThreads(START,END);
		long endTime = System.currentTimeMillis();
		System.out.println("#Primes: " + count + " Time: " + (endTime - startTime) + " ms");
	}

	private static long withThreads(long start, long end) throws InterruptedException {
		long[] bereich = new long[10];
		long limit = END/1000000;
		for(int i = 0; i <limit; i++){
			bereich[i] = start;
			start += 1_000_000L;
		}
		Thread[] threads = new Thread[5];
		int j = 0;
		for(int i = 0;i < 10;){
			int field = i;
			threads[j] = new Thread(()->{
				total += countPrimes(bereich[field],bereich[field+1]);
			});
			i = i + 2;
			j++;
		}
		for(int i = 0;i < 5;i++){
			threads[i].start();
			threads[i].join();
		}
		return total;
	}
}
