package edu.uprm.ece.icom4035.polynomialTesters;

import edu.uprm.ece.icom4035.polynomial.Polynomial.PolynomialImp;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PolynomialImp p1 = new PolynomialImp("9x^5+4x^3+2x^2");
		PolynomialImp p2 = new PolynomialImp("1");
		
		//PolynomialImp p1 = new PolynomialImp("5x^8+4x^6+3x^3+8x^2+6x+2");
		//PolynomialImp p2 = new PolynomialImp("5x^8+4x^6+4x^3+8x^2+6x+9");
		
		//Polynomial p3 = p1.add(p2);
		System.out.println(p2.add(p1));
		//System.out.println(p3.add(p2));
		
		//System.out.println(p2.subtract(p1));
		//System.out.println(p2.subtract(p2));
		
		//System.out.println(p2.multiply(p1));
		//System.out.println(p2.multiply(0));
		
		//System.out.println(p1.derivativ().derivative().derivative().derivative());
		//System.out.println(p1.derivative());
		//System.out.println(p1.indefiniteIntegral());
		//System.out.println(p2.definiteIntegral(-4, 4));
		//p1.multiply(2);
		//System.out.println(p2.definiteIntegral(2, 4)); // Must be 30
		
		//System.out.println(p1.definiteIntegral(-2, 2));
		//System.out.println(p1.indefiniteIntegral().evaluate(2)-p1.indefiniteIntegral().evaluate(-2));
	
		/*PolynomialImp testImp = new PolynomialImp("1x^2+2x");
		System.out.println("Original Polinomial" + testImp);
		System.out.println("Integral" + testImp.indefiniteIntegral());
		System.out.println("DefiniteIntegral: "+testImp.definiteIntegral(0, 2));
		System.out.println("WolframAlpha Value: 6.6666666667");*/
		
		//System.out.println(p1.equals(p2));
		
	
	}

}
