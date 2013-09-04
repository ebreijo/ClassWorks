package edu.uprm.ece.icom4035.polynomial.Polynomial;

/**
 * Polynomial interface defines methods with operations for a polynomial.
 * @author Eduardo
 */
public interface Polynomial extends Iterable<Term> {
	
	/**
	 * Add two polynomials by comparing the exponent of the terms 
	 * @param P2 The other polynomial
	 * @return A new polynomial (the sum of the two polynomials)
	 */
	public Polynomial add(Polynomial P2);
	
	/**
	 * Subtract two polynomials by comparing the exponent of the terms 
	 * @param P2 The other polynomial
	 * @return A new polynomial (the subtraction of the two polynomials)
	 */
	public Polynomial subtract(Polynomial P2);
	
	/**
	 * Multiply two polynomials and add terms that have the same exponential degree
	 * @param P2 The other polynomial 
	 * @return A new polynomial (the multiplication of the two polynomials)
	 */
	public Polynomial multiply(Polynomial P2);
	
	/**
	 * Multiply a constant c by a given polynomial
	 * @param c The number or constant to be multiplied
	 * @return A new polynomial (the multiplication of the constant by the polynomial)
	 */
	public Polynomial multiply(double c);

	/**
	 * Find the derivative of a given polynomial. y = x^a, y' = ax^(a-1)
	 * @return A new polynomial (the derivative of the given polynomial)
	 */
	public Polynomial derivative();
	
	/**
	 * Find the indefinite integral of a given polynomial. y' = x^a, integral of y = (x^(a+1))/(a+1)
	 * @return A new polynomial (the indefinite integral of the given polynomial)
	 */
	public Polynomial indefiniteIntegral();
	
	/**
	 * Find the indefinite integral by calling indefiniteIntegral method and evaluate that 
	 * integral by the the given interval. Given F'(x) = f(x) then, the integral from a to b 
	 * of f(x)dx = F(b) - F(a). 
	 * @param a A parameter of the interval
	 * @param b A parameter of the interval
	 * @return The result of the definite integral
	 */
	public double definiteIntegral(double a, double b);
	
	/**
	 * Get the largest degree of the term
	 * @return The largest degree
	 */
	public int degree();
	
	/**
	 * Evaluate each term of the polynomial by a given value
	 * @param x The value to be evaluated 
	 * @return The result of the evaluated polynomial
	 */
	public double evaluate(double x);

	/**
	 * Compare whether two polynomials are equals
	 * @param P2 the other polynomial
	 * @return True if they are equals, false otherwise
	 */
	public boolean equals(Polynomial P2);
	
}
