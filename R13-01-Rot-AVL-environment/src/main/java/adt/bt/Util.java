package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return - noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> newRoot = (BSTNode<T>) node.getRight();

		node.setRight(newRoot.getLeft());
		newRoot.setLeft(node);

		newRoot.setParent(node.getParent());
		node.setParent(newRoot);

		if (node.getRight() != null) {
			node.getRight().setParent(node);
		}

		if (newRoot.getParent() != null) {
			if (newRoot.getParent().getRight().equals(node)) {
				newRoot.getParent().setRight(newRoot);
			} else {
				newRoot.getParent().setLeft(newRoot);
			}
		}

		return newRoot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return noh que se tornou a nova raiz
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> newRoot = (BSTNode<T>) node.getLeft();
		node.setLeft(newRoot.getRight());
		newRoot.setRight(node);

		newRoot.setParent(node.getParent());
		node.setParent(newRoot);

		if (node.getLeft() != null) {
			node.getLeft().setParent(node);
		}

		if (newRoot.getParent() != null) {
			if (newRoot.getParent().getLeft().equals(node)) {
				newRoot.getParent().setLeft(newRoot);
			} else {
				newRoot.getParent().setRight(newRoot);
			}
		}

		return newRoot;
	}


	public static <T extends Comparable<T>> BSTNode<T> doubleRightRotation (BSTNode<T> node) {
		leftRotation((BSTNode<T>) node.getLeft());
		return rightRotation(node);
	}

	public static <T extends Comparable<T>> BSTNode<T> doubleLeftRotation (BSTNode<T> node) {
		rightRotation((BSTNode<T>) node.getRight());
		return leftRotation(node);
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
