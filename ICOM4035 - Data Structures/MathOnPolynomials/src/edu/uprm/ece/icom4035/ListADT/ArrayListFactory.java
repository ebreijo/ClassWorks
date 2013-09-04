package edu.uprm.ece.icom4035.ListADT;

public class ArrayListFactory<E> implements ListFactory<E> {

	private static final int DEFAULT_SIZE = 5;
	@Override
	public List<E> newInstance(int capacity) {
		return new ArrayList<E>(capacity);
	}

	@Override
	public List<E> newInstance() {
		return new ArrayList<E>(DEFAULT_SIZE);
	}
	

}
