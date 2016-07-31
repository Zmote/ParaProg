package uebung_05_aufgabe1;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class QuickSortSample_Parallelisierung {
	private static final int NOF_ELEMENTS = 50_000_000;

	private static int[] createRandomArray(int length) {
		Random random = new Random(4711);
		int[] numberArray = new int[length];
		for (int i = 0; i < length; i++) {
			numberArray[i] = random.nextInt();
		}
		return numberArray;
	}

	private static void checkSorted(int[] numberArray) {
		for (int i = 0; i < numberArray.length - 1; i++) {
			if (numberArray[i] > numberArray[i + 1]) {
				throw new RuntimeException("Not sorted");
			}
		}
	}

	public static void main(String[] args) {
		int[] numberArray = createRandomArray(NOF_ELEMENTS);
		ForkJoinPool threadPool = new ForkJoinPool();
		long startTime = System.currentTimeMillis();
		threadPool.invoke(new QuickSortTask(numberArray,0,NOF_ELEMENTS-1));
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time: " + (stopTime - startTime) + " ms");
		checkSorted(numberArray);
	}
}
