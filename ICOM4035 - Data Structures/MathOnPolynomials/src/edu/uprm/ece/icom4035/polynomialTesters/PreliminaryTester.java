package edu.uprm.ece.icom4035.polynomialTesters;

import edu.uprm.ece.icom4035.polynomial.Polynomial.PolynomialImp;

public class PreliminaryTester {

	public static void main(String[]args){
		PolynomialImp p1 = new PolynomialImp("2x^3+-4");
		PolynomialImp p2 = new PolynomialImp("x^3+6");
		PolynomialImp p3 = new PolynomialImp("x^8+-2x^5+x^4+1x^3+4");
		PolynomialImp p4 = new PolynomialImp("x^6+3x^4+x^2+5");


		System.out.println("P1: " + p1);
		System.out.println("P2: " + p2);
		System.out.println("P3: " + p3);
		System.out.println("P4: " + p4);

		System.out.println("\nSubstraction: P2 - P1");
		System.out.println(p2.subtract(p1));

		System.out.println("Substraction: P4 - P3");
		System.out.println(p4.subtract(p3));

		System.out.println("\nAddition: P1 + P2");
		System.out.println(p1.add(p2));

		System.out.println("\nAddition: P3 + P4");
		System.out.println(p3.add(p4));

		System.out.println("\nMultiplication: P1 * P2");
		System.out.println(p1.multiply(p2));

		System.out.println("\nMultiplication: P3 * P4");
		System.out.println(p3.multiply(p4));

		System.out.println("\nDegree of P1: " + p1.degree());

		System.out.println("\nP1 indefinite integral");
		System.out.println(p1.indefiniteIntegral());

		System.out.println("\nP2 indefinite integral");
		System.out.println(p2.indefiniteIntegral());

		System.out.println("\nP3 indefinite integral");
		System.out.println(p3.indefiniteIntegral());

		System.out.println("\nP4 indefinite integral");
		System.out.println(p4.indefiniteIntegral());

		System.out.println("\nP4 definite integral");
		System.out.println(p4.definiteIntegral(0, 20));

		System.out.println("\nEvaluating at 5 in P1: " + p1.evaluate(5));

		System.out.println("\nDerivating P1: " + p1.derivative());
		System.out.println("P1: "+ p1);

		PolynomialImp master;
		master = (PolynomialImp) p4.multiply(p3.add(p1));
		System.out.println("\nCurrent Master Polynomial: "+ master);
		master = (PolynomialImp) master.indefiniteIntegral();   
		System.out.println("\nCurrent Polynomial: "+ master);
		master = (PolynomialImp) master.multiply(0.1);
		System.out.println("\nCurrent Polynomial: "+ master);
		System.out.println("\nEvaluating at: a = -2 b = 45: " + (master.evaluate(-2)-master.evaluate(45)));


		//System.out.print("Integrating by -2 and 45: "+ master.definiteIntegral(-2, 45));


	}
}