package problems;

import adt.linkedList.SingleLinkedListNode;

public class LinkedListMergeImpl<T extends Comparable<T>> implements LinkedListMerge<T> {

	public SingleLinkedListNode<T> merge(SingleLinkedListNode<T> node1, SingleLinkedListNode<T> node2) {

		SingleLinkedListNode<T> saida = new SingleLinkedListNode<T>();
		SingleLinkedListNode<T>  noAtual = saida;

		SingleLinkedListNode<T> ponteiro1 = node1;
		SingleLinkedListNode<T> ponteiro2 = node2;

		while(!ponteiro1.isNIL() && !ponteiro2.isNIL()){
			if(ponteiro1.getData().compareTo(ponteiro2.getData())<0){
				noAtual.setNext(ponteiro1);
				ponteiro1 = ponteiro1.getNext();
			}

			else{
				noAtual.setNext(ponteiro2);
				ponteiro2 = ponteiro2.getNext();
			}

			noAtual = noAtual.getNext();
		}

		while(!ponteiro1.isNIL()){
			noAtual.setNext(ponteiro1);
			ponteiro1 = ponteiro1.getNext();
			noAtual = noAtual.getNext();
		}

		while(!ponteiro2.isNIL()){
			noAtual.setNext(ponteiro1);
			ponteiro1 = ponteiro1.getNext();
			noAtual = noAtual.getNext();
		}

		noAtual.setNext(new SingleLinkedListNode<>());
		saida = saida.getNext();

		return saida;

	}

		
}
