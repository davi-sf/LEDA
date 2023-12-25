package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;
import adt.hashtable.hashfunction.HashFunctionQuadraticProbing;

public class HashtableOpenAddressQuadraticProbingImpl<T extends Storable>
		extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		hashFunction = new HashFunctionQuadraticProbing<T>(size, method, c1, c2);
		this.initiateInternalTable(size);

	}

	public int hashIndex(T element, int probe) {
		return ((HashFunctionOpenAddress) this.hashFunction).hash(element, probe);
	}

	@Override
	public void insert(T element) {
		if (!isFull()) {
			int probe = 0;
			int index = hashIndex(element, probe);

			if (element != null) {
				while (this.table[index] != null && this.table[index] != this.deletedElement
						&& probe < this.table.length) {
					probe += 1;
					index = hashIndex(element, probe);
					this.COLLISIONS += 1;
				}

				if (probe < this.table.length) {
					this.table[index] = element;
					this.elements += 1;
				}
			}
		}

		else {
			throw new HashtableOverflowException();
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			int probe = 0;
			int index = hashIndex(element, probe);

			if (element != null) {
				while (index < this.table.length && this.table[index] != null && probe < this.table.length) {
					if (this.table[index].equals(element)) {
						this.table[index] = this.deletedElement;
						this.elements--;
						break;
					}
					probe++;
					index = hashIndex(element, probe);
				}
			}
		}
	}

	@Override
	public T search(T element) {
		T result = null;
		int indexOfElement = indexOf(element);

		if (!isEmpty() && element != null && indexOfElement > 0) {
			if (this.table[indexOfElement] instanceof DELETED) {
				result = null;
			} else {
				result = (T) this.table[indexOfElement];
			}
		}

		return result;
	}

	@Override
	public int indexOf(T element) {

		int exit = -1;

		if (!isEmpty() && element != null) {
			int probe = 0;
			int index = hashIndex(element, probe);

			while (probe < this.table.length && this.table[index] != null &&
					!this.table[index].equals(element) && this.table[index] != this.deletedElement) {
				probe += 1;
				index = hashIndex(element, probe);
			}

			if (probe < this.table.length) {
				exit = index;
			}
		}

		return exit;
	}
}
