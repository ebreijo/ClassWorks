package edu.uprm.ece.icom4035.ListADT;

public interface ListFactory<E> {
	
	public List<E> newInstance(int inicialCapacity);

	public List<E> newInstance();

}
