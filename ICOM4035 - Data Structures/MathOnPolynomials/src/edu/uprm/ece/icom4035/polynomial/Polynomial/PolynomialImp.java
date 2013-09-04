package edu.uprm.ece.icom4035.polynomial.Polynomial;

import java.util.Iterator;
import java.util.StringTokenizer;
import edu.uprm.ece.icom4035.ListADT.ArrayList;
import edu.uprm.ece.icom4035.ListADT.ArrayListFactory;
import edu.uprm.ece.icom4035.ListADT.List;
import edu.uprm.ece.icom4035.ListADT.ListFactory;


/**
 * The PolynomialImp class that implements Polynomial interface creates new Polynomials
 * and implements different operations to handle the Polynomials
 * @author Eduardo
 */
public class PolynomialImp implements Polynomial {
	
	// Private field
	private List<Term> terms; // A list of Term
	
	/**
	 * Construct an empty list of Term
	 */
	private PolynomialImp() {
		ListFactory<Term> factory = new ArrayListFactory<Term>();
		terms = factory.newInstance();
	}
	
	/**
	 * Construct a polynomial by a given string
	 * @param str A given string in form of polynomial
	 * (Precondition: str must be in the form "ax^n+ax^n-1+...+ax+a")
	 */
	public PolynomialImp(String str) {
		this();
		fromString(str);
	}

	/**
	 * Make the list iterable
	 */
	@Override
	public Iterator<Term> iterator() {
		return terms.iterator();
	}
	
	/**
	 * Add two polynomials by comparing the exponent of the terms
	 * Remove a term if the coefficient is equal to zero
	 * @param P2 The other polynomial
	 * @return A new polynomial (the sum of the two polynomials)
	 */
	@Override
	public Polynomial add(Polynomial P2) {
		List<Term> P2Terms = new ArrayList<Term>(5);
	
		for (Term term : P2) {			
			P2Terms.add(term);
		}
		
		List<Term> newTerms = new ArrayList<Term>(terms.size() + P2Terms.size());
		int i, j;
		for (i = 0, j = 0; i < terms.size() || j < P2Terms.size(); ) {
			
			if (i < terms.size() && j < P2Terms.size()) {
				
				if(this.terms.get(i).getExponent() == P2Terms.get(j).getExponent()) {
					newTerms.add(new TermImp(terms.get(i).getCoefficient() + P2Terms.get(j).getCoefficient(), 
							terms.get(i).getExponent()));
					i++;
					j++;	
				}	
				else if(this.terms.get(i).getExponent() > P2Terms.get(j).getExponent()) {
					newTerms.add(terms.get(i));
					i++;
				}
				else {
					newTerms.add(P2Terms.get(j));
					j++;
				}
			}
			else {
				if (i == terms.size()) {
					newTerms.add(P2Terms.get(j));
					j++;
				}
				else if (j == P2Terms.size()) {
					newTerms.add(this.terms.get(i));
					i++;
				}
				
			}
		}
		
		PolynomialImp result = new PolynomialImp();
		for (int index = 0; index < newTerms.size(); index++) {
			if (newTerms.get(index).getCoefficient() == 0) {
				newTerms.remove(index);
				index--;
			}
			else {
				result.addTerm(newTerms.get(index));
			}
		}		
		return result;
	}

	/**
	 * Subtract two polynomials by changing the signs of the second polynomial and by calling the add method
	 * @param P2 The other polynomial
	 * @return A new polynomial (the subtraction of the two polynomials)
	 */
	@Override
	public Polynomial subtract(Polynomial P2) {
		PolynomialImp negative = new PolynomialImp();
		for (Term term : P2) {			
			negative.addTerm((new TermImp(term.getCoefficient()*(-1), term.getExponent())));
		}
		return this.add(negative);
	}

	/**
	 * Multiply two polynomials and add terms that have the same exponential degree
	 * @param P2 The other polynomial 
	 * @return A new polynomial (the multiplication of the two polynomials)
	 */
	@Override
	public Polynomial multiply(Polynomial P2) {
		if (!P2.toString().equals("0.00")) {
			PolynomialImp temp = new PolynomialImp();
			for (Term t : P2) {
				for (int i = 0; i < terms.size(); i++) {
					temp.addTerm(new TermImp(terms.get(i).getCoefficient() * t.getCoefficient(), 
							terms.get(i).getExponent() + t.getExponent()));
				}
			}
			Polynomial result = new PolynomialImp("0");
			for (Term t : temp) {
				result = result.add(new PolynomialImp(t.toString()));
			}
			return result;
		}
		else {
			return new PolynomialImp("0");
		}
	}
	
	/**
	 * Multiply a constant c by a given polynomial
	 * @param c The number or constant to be multiplied
	 * @return A new polynomial (the multiplication of the constant by the polynomial)
	 */
	@Override
	public Polynomial multiply(double c) {
		if (c != 0) {
			PolynomialImp result = new PolynomialImp();
			for (int i = 0; i < terms.size(); i++) {
				result.addTerm(new TermImp(terms.get(i).getCoefficient() * c, terms.get(i).getExponent()));
			}
			return result;
		}
		return new PolynomialImp("0");
	}

	/**
	 * Find the derivative of a given polynomial. y = x^a, y' = ax^(a-1)
	 * @return A new polynomial (the derivative of the given polynomial)
	 */
	@Override
	public Polynomial derivative() {
		PolynomialImp result = new PolynomialImp();
		Term lastTerm = null;
		for (int i = 0; i < terms.size()-1; i++) {
			result.addTerm(new TermImp(terms.get(i).getCoefficient() * terms.get(i).getExponent(), 
					terms.get(i).getExponent() - 1));
		}
		lastTerm = new TermImp(terms.get(terms.size()-1).getCoefficient() * terms.get(terms.size()-1).getExponent(),
				terms.get(terms.size()-1).getExponent() - 1);
		if (lastTerm.getCoefficient() != 0) {
			result.addTerm(lastTerm);
		}
		return result;
	}

	/**
	 * Find the indefinite integral of a given polynomial. y' = x^a, integral of y = (x^(a+1))/(a+1)
	 * @return A new polynomial (the indefinite integral of the given polynomial)
	 */
	@Override
	public Polynomial indefiniteIntegral() {
		if (!this.toString().equals("0.00")) {
			PolynomialImp result = new PolynomialImp();

			for (int i = 0; i < terms.size(); i++) {
				result.addTerm(new TermImp(terms.get(i).getCoefficient() / (terms.get(i).getExponent() + 1), 
						terms.get(i).getExponent() + 1));
			}
			result.addTerm(new TermImp(1.00,0));
			return result;
		}
		else {
			return new PolynomialImp("1.00");
		}
	}

	/**
	 * Find the indefinite integral by calling indefiniteIntegral method and evaluate that 
	 * integral by the the given interval. Given F'(x) = f(x) then, the integral from a to b 
	 * of f(x)dx = F(b) - F(a). 
	 * @param a A parameter of the interval
	 * @param b A parameter of the interval
	 * @return The result of the definite integral
	 */
	@Override
	public double definiteIntegral(double a, double b) {
		Polynomial temp =  this.indefiniteIntegral();
		return temp.evaluate(b) - temp.evaluate(a);
	}

	/**
	 * Get the largest degree of the term
	 * @return The largest degree
	 */
	@Override
	public int degree() {
		return this.terms.get(0).getExponent();
	}

	/**
	 * Evaluate each term of the polynomial by a given value
	 * @param x The value to be evaluated 
	 * @return The result of the evaluated polynomial
	 */
	@Override
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.terms.size(); i++) {
			result += this.terms.get(i).evaluate(x);
		}
		return result;
	}
	
	/**
	 * Get a string in form of a polynomial and gets each term
	 * @param str A string in form of a polynomial
	 * (Precondition: str must be in the form "ax^n+ax^n-1+...+ax+a")
	 */
	private void fromString(String str) {
		StringTokenizer strTok = new StringTokenizer(str, "+");
		String nextStr = null;
		Term nextTerm = null;
		this.terms.clear();
		while (strTok.hasMoreElements()){
			nextStr = (String) strTok.nextElement();
			nextTerm = TermImp.fromString(nextStr);
			// private method to store a new term into a polynomial
			this.addTerm(nextTerm);
		}
	}

	/**
	 * Add a term to the list of Term
	 * @param nextTerm A Term
	 */
	private void addTerm(Term nextTerm) {
		this.terms.add(nextTerm);
	}
	
	/**
	 * Convert a polynomial into a string
	 */
	public String toString() {
		if(!terms.isEmpty()) {
			String str = "";
			for (int i = 0; i < this.terms.size() - 1; i++) {
				str += this.terms.get(i).toString() + "+";
			}

			str += this.terms.get(this.terms.size() - 1).toString();
			return str;	
		}
		else {
			return "0.00";
		}
	}
	
	/**
	 * Compare whether two polynomials are equals
	 * @param P2 the other polynomial
	 * @return True if they are equals, false otherwise
	 */
	@Override
	public boolean equals(Polynomial P2) {
		if (this.toString().equals(P2.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
}
