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

	public int heightRecursive(BSTNode<T> currentNode) {
		int height = -1;

		if (!currentNode.isEmpty()) {
			int leftHeight = heightRecursive((BSTNode<T>) currentNode.getLeft());
			int rightHeight = heightRecursive((BSTNode<T>) currentNode.getRight());

			height = 1 + Math.max(leftHeight, rightHeight);
		}

		return height;
	}

	@Override
	public int height() {
		return this.heightRecursive(this.root);
	}

	private BSTNode<T> searchRecursive(BSTNode<T> currentNode, T element) {
		BSTNode<T> foundNode;

		if (currentNode.isEmpty() || currentNode.getData().equals(element))
			foundNode = currentNode;
		else if (element.compareTo(currentNode.getData()) > 0)
			foundNode = this.searchRecursive((BSTNode<T>) currentNode.getRight(), element);
		else
			foundNode = this.searchRecursive((BSTNode<T>) currentNode.getLeft(), element);

		return foundNode;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> result;

		if (element == null) {
			result = new BSTNode<>();
		} else {
			result = this.searchRecursive(this.root, element);
		}

		return result;
	}

	private void insertRecursive(BSTNode<T> currentNode, T element) {
		if (currentNode.isEmpty()) {
			currentNode.setData(element);
			BSTNode<T> rightChild = new BSTNode.Builder<T>().parent(currentNode).build();
			BSTNode<T> leftChild = new BSTNode.Builder<T>().parent(currentNode).build();
			currentNode.setRight(rightChild);
			currentNode.setLeft(leftChild);
		} else {
			if (element.compareTo(currentNode.getData()) > 0) {
				this.insertRecursive((BSTNode<T>) currentNode.getRight(), element);
			} else {
				this.insertRecursive((BSTNode<T>) currentNode.getLeft(), element);
			}
		}
	}

	@Override
	public void insert(T element) {
		if (element != null)
			this.insertRecursive(this.root, element);
	}

	private BSTNode<T> maximumRecursive(BSTNode<T> currentNode) {
		BSTNode<T> result = currentNode;

		while (!result.getRight().isEmpty()) {
			result = (BSTNode<T>) result.getRight();
		}

		return result;
	}

	@Override
	public BSTNode<T> maximum() {
		if (this.isEmpty()) {
			return null;
		}

		BSTNode<T> result = this.maximumRecursive(this.root);
		return result;
	}

	private BSTNode<T> minimumRecursive(BSTNode<T> currentNode) {
		boolean result = currentNode.getLeft().isEmpty();

		if (result) {
			return currentNode;
		} else {
			return this.minimumRecursive((BSTNode<T>) currentNode.getLeft());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.minimumRecursive(this.root);
		}
	}

	private BSTNode<T> sucessorRecursive(BSTNode<T> currentNode, T element) {
		if (currentNode != null && currentNode.getData().compareTo(element) <= 0) {
			return this.sucessorRecursive((BSTNode<T>) currentNode.getParent(), element);
		} else {
			return currentNode;
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (!node.isEmpty()) {
			if (!node.getRight().isEmpty()) {
				node = this.minimumRecursive((BSTNode<T>) node.getRight());
			} else {
				node = this.sucessorRecursive(node, element);
			}
		}

		if (node == null || node.isEmpty()) {
			return null;
		} else {
			return node;
		}
	}

	private BSTNode<T> predecessorRecursive(BSTNode<T> currentNode, T element) {
		if (currentNode != null && currentNode.getData().compareTo(element) >= 0) {
			return this.predecessorRecursive((BSTNode<T>) currentNode.getParent(), element);
		} else {
			return currentNode;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);
		BSTNode<T> result = null;

		if (!node.isEmpty()) {
			if (!node.getLeft().isEmpty()) {
				result = this.maximumRecursive((BSTNode<T>) node.getLeft());
			} else {
				result = this.predecessorRecursive(node, element);
			}
		}

		if (result == null || result.isEmpty()) {
			return null;
		} else {
			return result;
		}
	}

	@Override
	public void remove(T element) {
		if (element == null) {
			return;
		}

		BSTNode<T> node = this.search(element);

		if (node.isEmpty()) {
			return;
		}

		if (node.isLeaf()) {
			node.setData(null);
			node.setLeft(null);
			node.setRight(null);
		} else if (node.getRight().isEmpty() || node.getLeft().isEmpty()) {
			BSTNode<T> childNode;
			if (node.getRight().isEmpty()) {
				childNode = (BSTNode<T>) node.getLeft();
			} else {
				childNode = (BSTNode<T>) node.getRight();
			}
			if (this.root.equals(node)) {
				this.root = childNode;
				this.root.setParent(null);
			} else {
				childNode.setParent(node.getParent());
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(childNode);
				} else {
					node.getParent().setRight(childNode);
				}
			}
		} else {
			T successor = this.sucessor(node.getData()).getData();
			this.remove(successor);
			node.setData(successor);
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
		return this.preOrderRecursive(this.root, new ArrayList<>());
	}

	private T[] orderRecursive (BSTNode<T> currentNode, List<T> list) {
		if (!currentNode.isEmpty()) {
			this.orderRecursive((BSTNode<T>) currentNode.getLeft(), list);
			list.add(currentNode.getData());
			this.orderRecursive((BSTNode<T>) currentNode.getRight(), list);
		}

		return (T[]) list.toArray(new Comparable[0]);
	}

	@Override
	public T[] order() {
		return this.orderRecursive(this.root, new ArrayList<>());
	}


	private T[] postOrderRecursive (BSTNode<T> currentNode, List<T> list) {
		if (!currentNode.isEmpty()) {
			this.postOrderRecursive((BSTNode<T>) currentNode.getLeft(), list);
			this.postOrderRecursive((BSTNode<T>) currentNode.getRight(), list);
			list.add(currentNode.getData());
		}

		return (T[]) list.toArray(new Comparable[0]);
	}

	@Override
	public T[] postOrder() {
		return this.postOrderRecursive(this.root, new ArrayList<>());
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
