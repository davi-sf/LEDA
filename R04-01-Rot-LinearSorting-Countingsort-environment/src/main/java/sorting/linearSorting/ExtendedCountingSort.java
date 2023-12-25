package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa do Counting Sort vista em sala. Desta vez este
 * algoritmo deve satisfazer os seguitnes requisitos:
 * - Alocar o tamanho minimo possivel para o array de contadores (C)
 * - Ser capaz de ordenar arrays contendo numeros negativos
 */
public class    ExtendedCountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		
		if(array != null){

			Integer [] b = new Integer[rightIndex-leftIndex+1];
			int [] c;


			int max = leftIndex;
			int min = leftIndex;
			
			for (int i = leftIndex; i <= rightIndex; i++) {
				if(array[i] > max){
					max = array[i];
				}
				else if(array[i] < min){
					min = array[i];
				}
			}

			int tamanhoArrayC = max - min+1;

			c = new int[tamanhoArrayC];  

			for (int i = leftIndex; i <= rightIndex; i++) {
				c[array[i]] += 1;
			}

			for (int i = 1; i <=max; i++) {
				c[i] += c[i-1];
			}

			for (int i = rightIndex; i >= leftIndex; i--) {
				c[array[i]] -=1;
				b[c[array[i].intValue()]] = array[i].intValue();
			}

			for (int i = leftIndex; i <=rightIndex; i++) {
				array[i] = b[i-leftIndex];
			}
		}
	}

}
