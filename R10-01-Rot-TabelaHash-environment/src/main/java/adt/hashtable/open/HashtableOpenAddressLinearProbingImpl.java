package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	private int hashIndex(T element, int probe) {
		return ((HashFunctionOpenAddress) this.hashFunction).hash(element, probe);
	}

	@Override
	public void insert(T element) {
		if (!isFull()) {
			int probe = 0;
			int index = hashIndex(element, probe);

			if (element != null) {
				while (index < this.table.length && this.table[index] != null
						&& this.table[index] != this.deletedElement) {
					probe += 1;
					index = hashIndex(element, probe);
					this.COLLISIONS++;
				}

				if (index < this.table.length) {
					this.table[index] = element;
					this.elements++;
				}
			}
		} else {
			throw new HashtableOverflowException();
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			int probe = 0;
			int index = hashIndex(element, probe);
			
			if (element != null) {
				while (probe < this.table.length && 
					   this.table[index] != null && 
					   this.table[index] != this.deletedElement && 
					   !this.table[index].equals(element)) {
					
					probe += 1;
					index = hashIndex(element, probe);
				}
		
				if (probe < this.table.length && 
					this.table[index] != null && 
					this.table[index].equals(element)) {
		
					this.table[index] = this.deletedElement;
					this.elements -= 1;
				}
			}
		}
	}
		

	@Override
	public T search(T element) {
		T exit = null;
		int indexof = indexOf(element);

		if (!isEmpty() && element != null && indexof >= 0) {
			if (this.table[indexof] instanceof DELETED) {
				exit = null;
			} else {
				exit = (T) this.table[indexof];
			}
		}

		return exit;
	}

	@Override
	public int indexOf(T element) {
		int exit = -1;

		if (!isEmpty() && element != null) {
			int probe = 0;
			int index = hashIndex(element, probe);

			while (index < this.table.length && this.table[index] != null
					&& this.table[index] != this.deletedElement && !this.table[index].equals(element)) {
				probe++;
				index = hashIndex(element, probe);
			}

			if (probe < this.table.length) {
				exit = index;
			}
		}

		return exit;

	}
}
