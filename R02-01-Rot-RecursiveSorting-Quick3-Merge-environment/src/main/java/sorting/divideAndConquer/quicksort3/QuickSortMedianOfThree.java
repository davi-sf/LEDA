package sorting.divideAndConquer.quicksort3;

import sorting.AbstractSorting;
import util.Util;

/**
 * A classe QuickSortMedianOfThree representa uma variação do QuickSort que
 * funciona de forma ligeiramente diferente. Relembre que quando o pivô
 * escolhido divide o array aproximadamente na metade, o QuickSort tem um
 * desempenho perto do ótimo. Para aproximar a entrada do caso ótimo, diversas
 * abordagens podem ser utilizadas. Uma delas é usar a mediana de 3 para achar o
 * pivô. Essa técnica consiste no seguinte:
 * 1. Comparar o elemento mais a esquerda, o central e o mais a direita do intervalo.
 * 2. Ordenar os elementos, tal que: A[left] < A[center] < A[right].
 * 3. Adotar o A[center] como pivô.
 * 4. Colocar o pivô na penúltima posição A[right-1].
 * 5. Aplicar o particionamento considerando o vetor menor, de A[left+1] até A[right-1].
 * 6. Aplicar o algoritmo na particao a esquerda e na particao a direita do pivô.
 */
public class QuickSortMedianOfThree<T extends Comparable<T>> extends
		AbstractSorting<T> {

	public void sort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex < rightIndex){
			int pivotIndex = particionamento (array,leftIndex, rightIndex);
			sort(array, leftIndex, pivotIndex-1);
			sort(array, pivotIndex+1, rightIndex);
		}

	}

	public int particionamento(T [] array, int left, int right){
		int indicePivot = mediana_de_tres(array, left, right);
		T pivot = array[indicePivot];
		Util.swap(array, indicePivot, left);
		int i = left;
		for (int j = left+1; j <= right; j++) {
			if(array[j].compareTo(pivot)<=0){
				i++;
				Util.swap(array, i, j);
			}
			
		}

		Util.swap(array, left, i);

		return i;
	
	}

	private int mediana_de_tres(T[] array, int left, int right) {
		int middle = (left+right)/2;
		if(array[left].compareTo(array[right])>0){
			Util.swap(array, left, right);
		}

		if(array[left].compareTo(array[middle])>0){
			Util.swap(array, left, middle);
		}

		if(array[middle].compareTo(array[right])>0){
			Util.swap(array, middle, right);
		}

		return middle;
	}




}

