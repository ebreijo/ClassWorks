package edu.uprm.ece.icom4035.polynomialTesters;

import java.util.Random;

import edu.uprm.ece.icom4035.polynomial.Polynomial.PolynomialImp;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		for (int i = 0; i <= 10000; i++){
			String p1 = "";
			String p2 = "";
			Random random = new Random();
			int eval = random.nextInt();
			int a = random.nextInt();
			int b = random.nextInt();
			int degree = random.nextInt();
			for (int j = 10; j >= 0; j--)
			{
				degree = random.nextInt(999999);
				double coeff = random.nextDouble();
				boolean isNegative = random.nextBoolean();
				if (isNegative){
					coeff *= -1; }
				if (j == 0){
					p1 += coeff; }
				else if (j == 1){
					p1 += coeff + "x+"; }
				else{
					p1 += coeff + "x^" + degree + "+";  }
			}
			for (int j = 10; j >= 0; j--) {
				double coeff = random.nextDouble();
				boolean isNegative = random.nextBoolean();
				if (isNegative){
					coeff *= 1;}
				if (j == 0){
					p2 += coeff;
				}
				else if (j == 1) {
					p2 += coeff + "x+";
				}
				else{
					p2 += coeff + "x^" + degree + "+";
				}
			}
			if (random.nextBoolean())
				eval *= -1;
			if (random.nextBoolean())
				a *= -1;
			if (random.nextBoolean())
				b *= -1;
			PolynomialImp P1 = new PolynomialImp(p1);
			PolynomialImp P2 = new PolynomialImp(p2);
			System.out.println("Add: " + P1 + " " + P2);
			System.out.println(P1.add(P2));
			System.out.println();
			System.out.println("Substract: " + P1 + " " + P2);
			System.out.println(P1.subtract(P2));
			System.out.println();
			System.out.println("Multiply: " + P1 + " " + P2);
			System.out.println(P1.multiply(P2));
			System.out.println();
			System.out.println("Scalar Multiply: " + P1 + " " + eval);
			System.out.println(P1.multiply(eval));
			System.out.println();
			System.out.println("Derive: " + P1);
			System.out.println(P1.derivative());
			System.out.println();
			System.out.println("Integrate: " + P1);
			System.out.println(P1.indefiniteIntegral());
			System.out.println();
			System.out.println("Definite Integral: " + P1 + " from " + b + " to " + a);
			System.out.println(P1.definiteIntegral(a, b));
			System.out.println();
			System.out.println("Degree: " + P1);
			System.out.println(P1.degree());
			System.out.println();
			System.out.println("Evaluate: " + P1 + " at " + eval);
			System.out.println(P1.evaluate(eval));
			System.out.println("P1 string: " + P1);
			System.out.println("P2 string: " + P2);
			System.out.println("\n\n\n\n----NEW ITERATION----\n\n\n\n\n");
		}
//		System.out.println(new PolynomialImp("0").degree());
	}

}
