package adt.avltree;

import adt.bst.BSTVerifierImpl;

/**
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeVerifierImpl<T extends Comparable<T>> extends BSTVerifierImpl<T> implements AVLTreeVerifier<T> {

	private AVLTreeImpl<T> avlTree;

	public AVLTreeVerifierImpl(AVLTree<T> avlTree) {
		super(avlTree);
		this.avlTree = (AVLTreeImpl<T>) avlTree;
	}

	private AVLTreeImpl<T> getAVLTree() {
		return avlTree;
	}

	@Override
	public boolean isAVLTree() {

		boolean resp = true;

		if (!currentNode.isEmpty()) {
			int balance = this.avlTree.calculateBalance(currentNode);
			if (Math.abs(balance) <= 1) {
				boolean leftIsAVL = this.isAVLRecursive((BSTNode<T>) currentNode.getLeft());
				boolean rightIsAVL = this.isAVLRecursive((BSTNode<T>) currentNode.getRight());

				resp = leftIsAVL && rightIsAVL;
			} else {
				resp = false;
			}
		}

		return resp;

	}
}