package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala.
 *
 * Procure evitar desperdício de memória: AO INVÉS de alocar o array de contadores
 * com um tamanho arbitrariamente grande (por exemplo, com o maior valor de entrada possível),
 * aloque este array com o tamanho sendo o máximo inteiro presente no array a ser ordenado.
 *
 * Seu algoritmo deve assumir que o array de entrada nao possui numeros negativos,
 * ou seja, possui apenas numeros inteiros positivos e o zero.
 *
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		
		if(array != null){

			Integer [] b = new Integer[rightIndex-leftIndex+1];
			int c [];


			int max = leftIndex;
			
			for (int i = leftIndex; i <= rightIndex; i++) {
				if(array[i] > max){
					max = array[i];
				}
			}

			c = new int[max+1];

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
