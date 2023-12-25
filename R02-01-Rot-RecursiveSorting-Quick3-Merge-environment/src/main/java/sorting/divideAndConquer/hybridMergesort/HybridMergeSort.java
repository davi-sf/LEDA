package sorting.divideAndConquer.hybridMergesort;

import sorting.AbstractSorting;
import java.util.Arrays;

/**
 * A classe HybridMergeSort representa a implementação de uma variação do
 * MergeSort que pode fazer uso do InsertionSort (um algoritmo híbrido) da
 * seguinte forma: o MergeSort é aplicado a entradas maiores a um determinado
 * limite. Caso a entrada tenha tamanho menor ou igual ao limite o algoritmo usa
 * o InsertionSort.
 * 
 * A implementação híbrida deve considerar os seguintes detalhes:
 * - Ter contadores das quantidades de MergeSorts e InsertionSorts aplicados, de forma
 *   que essa informação possa ser capturada pelo teste.
 * - A cada chamado do método de sort(T[] array) esses contadores são resetados. E a cada chamada
 *   interna de um merge ou insertion, os contadores MERGESORT_APPLICATIONS e
 *   INSERTIONSORT_APPLICATIONS são incrementados.
 * - O InsertionSort utilizado no algoritmo híbrido deve ser in-place.
 */
public class HybridMergeSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * For inputs with size less or equal to this value, the insertionsort
	 * algorithm will be used instead of the mergesort.
	 */
	public static final int SIZE_LIMIT = 4;

	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;

	public void sort(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex >= 0 && leftIndex < rightIndex && rightIndex < array.length) {
			MERGESORT_APPLICATIONS = 0;
			INSERTIONSORT_APPLICATIONS = 0;

			if((rightIndex-leftIndex)+1 > SIZE_LIMIT){
			insertionSort(array,leftIndex,rightIndex);
			mergeSort(array, leftIndex, rightIndex);
		}
		else{

			mergeSort(array,leftIndex,rightIndex);
			insertionSort(array, leftIndex, rightIndex);
		}
	}
}


	private void mergeSort(T[] array, int leftIndex, int rightIndex) {

		if(leftIndex >= rightIndex){
			return;
		}
		int midIndex = (leftIndex + rightIndex) /2;
		mergeSort(array, leftIndex, midIndex);
		mergeSort(array, midIndex+1, rightIndex);
		divide(array, leftIndex, midIndex, rightIndex);
	}

	private void divide(T[] array, int leftIndex,int midIndex, int rightIndex) {
		T[] merged = Arrays.copyOfRange(array, leftIndex, rightIndex+1);
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

		for (int k2 = 0; k2 < merged.length; k2++) {
			array[leftIndex + k2] = merged[k2];
			
		}
	}

	private void insertionSort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex >= rightIndex){
			return;
		}

		if(leftIndex < 0){
			return;
		}

		for (int i = leftIndex; i <= rightIndex; i++) {
			T current = array[i];
			int j = i-1;
			while(j >= 0 && array[j].compareTo(current)>0){
				array[j+1] = array[j];
				j--;
				array[j+1] = current;
			}
			
		}

	}
}
