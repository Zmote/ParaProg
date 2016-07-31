package uebung_05_aufgabe1;

import java.util.concurrent.RecursiveAction;

public class QuickSortTask extends RecursiveAction {
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 100000;
	private int[] array;
	private int left;
	private int right;
	
	public QuickSortTask(int[] array,int left, int right){
		this.array = array;
		this.left = left;
		this.right = right;
	}

	@Override
	protected void compute() {
		quickSort(left,right);
	}

	// sorts the partition between array[left] and array[right]
	private void quickSort(int left, int right) {
		// split into two partitions
		int i = left, j = right;
		long m = array[(left + right) / 2];
		do {
			while (array[i] < m) {
				i++;
			}
			while (array[j] > m) {
				j--;
			}
			if (i <= j) {
				int t = array[i];
				array[i] = array[j];
				array[j] = t;
				i++;
				j--;
			}
		} while (i <= j);
		// recursively sort the two partitions
		if(j - left > THRESHOLD && right - i > THRESHOLD){
			invokeAll(
				new QuickSortTask(array,left,j),
				new QuickSortTask(array,i,right)
			);
			
		}else{
			if (j > left) {
				quickSort(left,j);
			}
			if (i < right) {
				quickSort(i,right);
			}
		}
	}
}
