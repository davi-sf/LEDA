package adt.bst.extended;

import adt.bst.BSTImpl;
import adt.bt.BTNode;

/**
 * Note que esta classe estende sua implementacao de BST (BSTImpl).
 * Dependendo do design que voce use, sua BSTImpl precisa ter apenas funcionando
 * corretamente o metodo insert para que voce consiga testar esta classe.
 */
public class FloorCeilBSTImpl extends BSTImpl<Integer> implements FloorCeilBST {

	private Integer recursiveFloor(BTNode<Integer> currentNode, double number) {
		Integer floor = -1;
		if (!currentNode.isEmpty()) {
			if (number > currentNode.getData())
				floor = recursiveFloor(currentNode.getRight(), number);
			else if (number < currentNode.getData())
				floor = recursiveFloor(currentNode.getLeft(), number);
			else
				floor = currentNode.getData();
		}
		return floor;
	}

	@Override
	public Integer floor(Integer[] array, double numero) {
		return recursiveFloor(root, numero);
	}

	private Integer recursiveCeil(BTNode<Integer> currentNode, double number) {
		Integer ceil = -1;

		if (!currentNode.isEmpty()) {
			if (number < currentNode.getData())
				ceil = recursiveCeil(currentNode.getLeft(), number);
			else if (number > currentNode.getData())
				ceil = recursiveCeil(currentNode.getRight(), number);
			else
				ceil = currentNode.getData();
		}

		return ceil;
	}

	@Override
	public Integer ceil(Integer[] array, double numero) {
		return recursiveCeil(root, numero);

	}
}