package edu.uprm.ece.icom4035.polynomial.Polynomial;

import java.util.StringTokenizer;

import edu.uprm.ece.icom4035.ListADT.ArrayList;
import edu.uprm.ece.icom4035.ListADT.List;


/**
 * The TermImp class that implements Term interface creates new terms with coefficients and exponents
 * @author Eduardo
 */
public class TermImp implements Term {
	
	private double coefficient; // private field for a coefficient
	private int exponent; // private field for an exponent
	
	/**
	 * Construct a term by a given coefficient and a given exponent
	 * @param coeff A coefficient
	 * @param exp An exponent
	 */
	public TermImp(double coeff, int exp) {
		coefficient = coeff;
		exponent = exp;
	}
	
	/**
	 * Get the coefficient of a term
	 * @return the coefficient 
	 */
	@Override
	public double getCoefficient() {
		return coefficient;
	}

	/**
	 * Get the exponent of a term
	 * @return the exponent
	 */
	@Override
	public int getExponent() {
		return exponent;
	}

	/**
	 * Evaluate a term by a given value. c = constant, a(c)^n 
	 * @param x The value to be evaluated 
	 * @return The result of the evaluated term 
	 */
	@Override
	public double evaluate(double x) {
		return coefficient * Math.pow(x, exponent);
	}
	
	/**
	 * Convert a term into a string
	 */
	public String toString() {
		if (this.exponent == 0){
			return String.format("%.2f", this.coefficient);

		}
		else if (this.exponent == 1){
			return String.format("%.2fx", this.coefficient);
		}
		else {
			return String.format("%.2fx^%d", this.coefficient, this.exponent);
		}
	}
	
	/**
	 * Get a the coefficient and exponent of a string that contains a term.
	 * Handle different ways of terms "ax^n", "x^n", "ax", "x", "a". 
	 * @param str A string that contains a term
	 * @return A new term with coefficient and exponent
	 */
	public static Term fromString(String str){
		String temp = new String(str);
		TermImp result = null;
		if (temp.contains("x^")){
			// handle term with the form ax^n
			StringTokenizer strTok = new StringTokenizer(temp, "x^");
			List<String> list = new ArrayList<String>(2);
			while(strTok.hasMoreElements()){
				list.add((String) strTok.nextElement());
			}
			
			if (list.size() == 0){
				throw new IllegalArgumentException("Argument string is formatter illegally.");
			}
			else if (list.size() == 1){
				// term if of the form x^n, where n is the exponent
				Integer expo = Integer.parseInt(list.get(0));
				result = new TermImp(1, expo);
			}
			else {
				// term if of the form ax^n, where a, (a != 1) is the coefficient and n is the exponent
				Double coeff = Double.parseDouble(list.get(0));
				Integer expo = Integer.parseInt(list.get(1));
				result = new TermImp(coeff, expo);
			}			
		}
		else if (temp.contains("x")){
			// handle value with exponent == 1
			StringTokenizer strTok = new StringTokenizer(temp, "x");
			List<String> list = new ArrayList<String>(2);
			while(strTok.hasMoreElements()){
				list.add((String) strTok.nextElement());
			}
			if (list.size() == 0){
				// term is of the form x, with coefficient = 1 and exponent = 1
				result = new TermImp(1.0, 1);
			}
			else {
				// term is of the form ax, with coefficient = a and exponent = 1
				Double coeff = Double.parseDouble(list.get(0));
				result = new TermImp(coeff, 1);
			}	
		}
		else {
			// handle numeric value
			result = new TermImp(Double.parseDouble(temp), 0);
		}
		return result;
	}


}
