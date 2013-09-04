package edu.uprm.ece.icom4035.polynomial.Polynomial;

/**
 * Term interface defines method for a term
 * @author Eduardo
 */
public interface Term {
	
	/**
	 * Get the coefficient of a term
	 * @return the coefficient 
	 */
	public double getCoefficient();
	
	/**
	 * Get the exponent of a term
	 * @return the exponent
	 */
	public int getExponent();
	
	/**
	 * Evaluate a term by a given value. c = constant, a(c)^n 
	 * @param x The value to be evaluated 
	 * @return The result of the evaluated term 
	 */
	public double evaluate(double x);
	
}
