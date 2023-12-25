package sorting.divideAndConquer;

import sorting.AbstractSorting;
import java.util.Arrays;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex >= rightIndex){
			return;
		}
		int midIndex = (leftIndex + rightIndex) /2;
		sort(array, leftIndex, midIndex);
		sort(array, midIndex+1, rightIndex);
		divide(array,leftIndex,midIndex,rightIndex);
		

}

	private void divide(T[] array, int leftIndex, int midIndex, int rightIndex) {
		T [] merged = Arrays.copyOfRange(array, leftIndex, rightIndex+1);
		int i = leftIndex;
		int j = midIndex +1;
		int k = 0;
		while(i <= midIndex && j <= rightIndex){
			if(array[i].compareTo(array[j])<=0){
				merged[k] = array[i];
				i++;
				k++;
			}
			else{
				merged[k] = array[j];
				j++;
				k++;
			}
		}

		while(i <= midIndex){
			merged[k] = array[i];
				i++;
				k++;
		}
		while(j <= rightIndex){
			merged[k] = array[j];
				j++;
				k++;
		}

		for (int k2 = 0; k2 <merged.length; k2++) {
			array[leftIndex +k2] = merged[k2];
			
		}
	
	}
}

