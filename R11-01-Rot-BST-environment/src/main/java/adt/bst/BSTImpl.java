package adt.bst;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}



	private int recursiveHeight(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		}
	
		int leftHeight = recursiveHeight((BSTNode<T>) node.getLeft());
		int rightHeight = recursiveHeight((BSTNode<T>) node.getRight());
	
		return Math.max(leftHeight, rightHeight) + 1;
	}
	

	@Override
	public int height() {
		return this.recursiveHeight(root);
	}

	private BSTNode<T> searchRecursive(BSTNode<T> node, T targetElement) {
		if (node.isEmpty() || node.getData().equals(targetElement))
			return node;
		else if (targetElement.compareTo(node.getData()) > 0)
			return this.searchRecursive((BSTNode<T>) node.getRight(), targetElement);
		else
			return this.searchRecursive((BSTNode<T>) node.getLeft(), targetElement);
	}

	@Override
	public BSTNode<T> search(T element) {

		BSTNode<T> elementFound = new BSTNode<>();

		if (element != null) {
			elementFound = this.searchRecursive(root, element);
		}
		return elementFound;
	}

	@Override
	public void insert(T element) {
		this.insertRecursive(root, element);
	}

	private void insertRecursive(BSTNode<T> node, T element) {
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				BSTNode<T> rightChild = new BSTNode.Builder<T>().parent(node).build();
				BSTNode<T> leftChild = new BSTNode.Builder<T>().parent(node).build();
				node.setRight(rightChild);
				node.setLeft(leftChild);
			} else {
				if (element.compareTo(node.getData()) > 0)
					this.insertRecursive((BSTNode<T>) node.getRight(), element);
				else
					this.insertRecursive((BSTNode<T>) node.getLeft(), element);
			}

		}

	}

	private BSTNode<T> maxRecursive(BSTNode<T> node) {
		BSTNode<T> maximumNode = null;

		while (!node.isEmpty()) {
			maximumNode = node;
			node = (BSTNode<T>) node.getRight();

		}

		return maximumNode;
	}

	@Override
	public BSTNode<T> maximum() {

		return maxRecursive(root);
	}

	private BSTNode<T> minRecursive(BSTNode<T> rootNode) {
		BSTNode<T> leftNode = null;
		while (!rootNode.isEmpty()) {
			leftNode = rootNode;
			rootNode = (BSTNode<T>) rootNode.getLeft();
		}

		return leftNode;
	}

	@Override
	public BSTNode<T> minimum() {
		return minRecursive(root);
	}

	private BSTNode<T> sucessorRecursive(BSTNode<T> currentNode, T element) {
		if (currentNode != null && currentNode.getData().compareTo(element) <= 0)
			return sucessorRecursive((BSTNode<T>) currentNode.getParent(), element);
		else
			return currentNode;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty())
				node = this.minRecursive((BSTNode<T>) node.getRight());
			else
				node = this.sucessorRecursive(node, element);
		}

		return (node == null || node.isEmpty()) ? null : node;
	}


	private BSTNode<T> predecessorRecursive(BSTNode<T> currentNode, T element) {
		if (currentNode != null && currentNode.getData().compareTo(element) >= 0)
			return this.predecessorRecursive((BSTNode<T>) currentNode.getParent(), element);
		else
			return currentNode;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty())
				node = this.maxRecursive((BSTNode<T>) node.getLeft());
			else
				node = this.predecessorRecursive(node, element);
		}

		return (node == null || node.isEmpty()) ? null : node;
	}

	@Override
	public void remove(T element) {
		if (element != null) {
			BSTNode<T> targetNode = this.search(element);

			if (!targetNode.isEmpty()) {
				if (targetNode.isLeaf()) {
					targetNode.setData(null);
					targetNode.setLeft(null);
					targetNode.setRight(null);
				} else if (targetNode.getRight().isEmpty() || targetNode.getLeft().isEmpty()) {
					BSTNode<T> childNode = targetNode.getRight().isEmpty() ? (BSTNode<T>) targetNode.getLeft()
							: (BSTNode<T>) targetNode.getRight();

					if (this.root.equals(targetNode)) {
						this.root = childNode;
						this.root.setParent(null);
					} else {
						childNode.setParent(targetNode.getParent());
						if (targetNode.getParent().getLeft().equals(targetNode))
							targetNode.getParent().setLeft(childNode);
						else
							targetNode.getParent().setRight(childNode);
					}
				} else {
					T successor = this.sucessor(targetNode.getData()).getData();
					this.remove(successor);
					targetNode.setData(successor);
				}
			}
		}
	}

	private T[] preOrderRecursive(BSTNode<T> currentNode, List<T> list) {
		if (!currentNode.isEmpty()) {
			list.add(currentNode.getData());
			this.preOrderRecursive((BSTNode<T>) currentNode.getLeft(), list);
			this.preOrderRecursive((BSTNode<T>) currentNode.getRight(), list);
		}
		return (T[]) list.toArray(new Comparable[0]);
	}

	@Override
	public T[] preOrder() {
		return preOrderRecursive(root, new ArrayList<>());
	}

	private void orderRecursive(ArrayList<T> list, BSTNode<T> node) {
		if (!node.isEmpty()) {
			orderRecursive(list, (BSTNode<T>) node.getLeft());
			list.add(node.getData());
			orderRecursive(list, (BSTNode<T>) node.getRight());
		}
	}

	@Override
	public T[] order() {
		ArrayList<T> list = new ArrayList<>();
		orderRecursive(list, root);
		T[] array = (T[]) list.toArray(new Comparable[list.size()]);
		return array;
	}

	private void postOrderRecursive(ArrayList<T> list, BSTNode<T> node) {
		if (!node.isEmpty()) {
			BSTNode<T> leftChild = (BSTNode<T>) node.getLeft();
			BSTNode<T> rightChild = (BSTNode<T>) node.getRight();

			postOrderRecursive(list, leftChild);
			postOrderRecursive(list, rightChild);
			list.add(node.getData());
		}
	}

	@Override
	public T[] postOrder() {
		ArrayList<T> list = new ArrayList<>();
		postOrderRecursive(list, root);
		T[] array = (T[]) new Comparable[list.size()]; // Cria um array de Comparable
		return list.toArray(array);
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
