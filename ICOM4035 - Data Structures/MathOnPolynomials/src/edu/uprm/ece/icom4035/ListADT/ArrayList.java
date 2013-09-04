package edu.uprm.ece.icom4035.ListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E> {

	private int currentSize;
	private E elements[];
	
	private class ListIterator<E> implements Iterator<E> {
		
		private int currentPosition;

		private ListIterator() {
			currentPosition = 0;
		}
		
		@Override
		public boolean hasNext() {
			return currentPosition < currentSize;
		}

		@Override
		public E next() {
			if(hasNext()) {
				return (E) elements[currentPosition++];
			}
			else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(); 
		}		
	}
	
	public ArrayList(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Size must be at least 1!");
		}
		this.elements = (E[]) new Object[initialCapacity];
		this.currentSize = 0;
	}
	
	@Override
	public Iterator<E> iterator() {
		return new ListIterator<E>();
	}

	/**
	 * Grows the size of the elements array
	 */
	private void reallocate() {
		E newElements[] = (E[]) new Object[2 * this.elements.length];
		for (int i = 0; i < currentSize; i++) {
			newElements[i] = this.elements[i];
		}
		this.elements = newElements;
	}
	@Override
	public void add(E obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Value cannot be null!");
		}
		else if (this.currentSize == this.elements.length) {
			reallocate();
		}
		elements[this.currentSize++] = obj;
		
	}

	@Override
	public void add(int index, E obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Value cannot be null!");
		}
		else if(index == currentSize) {
			this.add(obj);
		}
		else if (index >= 0 && index < this.currentSize) {
			if (this.currentSize == this.elements.length) {
				reallocate();
			}
			for (int i = this.currentSize; i > index; i--) {
				this.elements[i] = this.elements[i-1];
			}
			this.elements[index] = obj;
			currentSize++;
		}	
		else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public boolean remove(E obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Value cannot be null!");
		}
		else {
			int target = this.firstIndex(obj);
			if (target == -1) {
				return false;
			}
			else {
				return remove(target);
			}
		}
		
	}

	@Override
	public boolean remove(int index) {
		if(index >= 0 && index < currentSize) {
			for (int i = index; i < this.currentSize - 1; i++) {
				this.elements[i] = this.elements[i+1];
			}
			elements[--this.currentSize] = null;
			return true;
		}
		else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	@Override
	public int removeAll(E obj) {
		int counter = 0;
		while(this.remove(obj)) {
			counter++;
		}
		return counter;
	}

	/**
	 * if (index >= 0 && index < currentSize) { 
	 * 		return this.elements[index];
	 * 	}
	 * 	else {
	 * 		throw new ArrayIndexOutOfBoundsException();
	 * 	}
	 */
	@Override
	public E get(int index) {
		if(index < 0 || index >= currentSize) {
			throw new ArrayIndexOutOfBoundsException();
		}
		else {
			return this.elements[index];
		}
	}

	@Override
	public E set(int index, E obj) {
		if(index < 0 || index >= currentSize) {
			throw new ArrayIndexOutOfBoundsException();
		}
		else {
			// Return the old value
			E temp = this.elements[index];
			this.elements[index] = obj;
			return temp;
		}
	}

	@Override
	public E first() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			return this.elements[0];
		}
	}

	@Override
	public E last() {
		if(this.isEmpty()) {
			return null;
		}
		else {
			return this.elements[this.currentSize - 1];
		}
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public boolean contains(E obj) {
		return firstIndex(obj) >= 0;
	}

	/**
	 * Return the position where the first element was found, else return -1
	 */
	@Override
	public int firstIndex(E obj) {
		for (int i = 0; i < this.size(); i++) {
			if(elements[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndex(E obj) {
		for (int i = this.size() - 1; i >= 0; i--) {
			if(elements[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.size(); i++) {
			elements[i] = null;
		}
		currentSize = 0;	
	}

	@Override
	public int size() {
		return this.currentSize;
	}
}
