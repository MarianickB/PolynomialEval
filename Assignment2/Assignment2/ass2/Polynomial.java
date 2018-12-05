package ass2;

import java.math.BigInteger;

public class Polynomial {
	private SLinkedList<Term> polynomial;

	public int size() {
		return polynomial.size();
	}

	private Polynomial(SLinkedList<Term> p) {
		polynomial = p;
	}

	public Polynomial() {
		polynomial = new SLinkedList<Term>();
	}

	// Returns a deep copy of the object.
	public Polynomial deepClone() {
		return new Polynomial(polynomial.deepClone());
	}

	/*
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is in
	 * decreasing order of exponent.
	 */
	public void addTerm(Term t) {
		/**** ADD CODE HERE ****/
		
		
		if(polynomial.size() == 0) {
			polynomial.addFirst(t);
			return;
		}

		int count = 0;

		for (Term currentTerm: polynomial) {
			
				
			if (t.getExponent() == currentTerm.getExponent()) {
				
				currentTerm.setCoefficient(currentTerm.getCoefficient().add(t.getCoefficient()));
					
					if(currentTerm.getCoefficient().compareTo(new BigInteger("0")) == 0) {
						polynomial.remove(count);
						return;
					}
					
				return;
			}
			
			
			if(t.getExponent() > currentTerm.getExponent()) {
				polynomial.add(count, t);
				return;
				
			}
			
			
			count++;
			
		}
		
		polynomial.addLast(t);

		// Hint: Notice that the function SLinkedList.get(index) method is O(n),
		// so if this method were to call the get(index)
		// method n times then the method would be O(n^2).
		// Instead, use a Java enhanced for loop to iterate through
		// the terms of an SLinkedList.
		/*
		 * for (Term currentTerm: polynomial) { // The for loop iterates over each term
		 * in the polynomial!! // Example: System.out.println(currentTerm.getExponent())
		 * should print the exponents of each term in the polynomial when it is not
		 * empty. }
		 */
	}

	public Term getTerm(int index) {
		return polynomial.get(index);
	}

	// TODO: Add two polynomial without modifying either
	/*public static Polynomial add(Polynomial p1, Polynomial p2) {
		/**** ADD CODE HERE ****/
	public static Polynomial add(Polynomial p1, Polynomial p2) {
		
		//Polynomial result = new Polynomial();
		Polynomial result = new Polynomial();
		SLinkedList<Term> p01 = p1.polynomial.deepClone();
		SLinkedList<Term> p02 = p2.polynomial.deepClone();
		
		while (true) {
			
			Term t1 = p01.get(0);
			Term t2 = p02.get(0);
			Term t3;
			
			if (t1.getExponent() == t2.getExponent()) {
							
				t3 = new Term( t2.getExponent(), t1.getCoefficient().add(t2.getCoefficient()));
								
				if(t3.getCoefficient().compareTo(new BigInteger("0")) == 0) {
					p01.removeFirst();
					p02.removeFirst();
				}
				
				else {
					
				result.addTermLast(t3);
				p01.removeFirst();
				p02.removeFirst();
				
				}
			}
			
			else if(t1.getExponent() > t2.getExponent()) {
				result.addTermLast(t1);
				p01.removeFirst();
				
			}
			
			else if (t1.getExponent() < t2.getExponent()) {
				result.addTermLast(t2);
				p02.removeFirst();
				
			}
			
			
			if(p02.size() == 0 || p01.size() == 0) {
				break;
			}
			
		}
		
		if ((p02.size() == 0) && p01.size() > 0) {

			for(Term currentTerm : p01) {
				result.addTermLast(currentTerm);
			}
		}
		
		if ((p01.size() == 0) && p02.size() > 0) {

			for(Term currentTerm : p02) {
				result.addTermLast(currentTerm);
			}
		}
				
		
		return result;
	}

	// TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t) {
	/**** ADD CODE HERE ****/	
		
		for (Term currentTerm: polynomial) {
			BigInteger coeff = t.getCoefficient().multiply(currentTerm.getCoefficient());
			int exp = t.getExponent() + currentTerm.getExponent();
			
			currentTerm.setCoefficient(coeff);
			currentTerm.setExponent(exp);
		}
	}

	// TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2) {
		/**** ADD CODE HERE ****/
		
		Polynomial result = new Polynomial();
		Polynomial p01 = p1.deepClone();
		Polynomial p02 = p2.deepClone();
		Polynomial org = p02.deepClone();
		
		for (Term currentTerm: p01.polynomial) {
			
			p02.multiplyTerm(currentTerm);
			result = Polynomial.add(p02, org);
						
			}
			
		return result;
	}

	// TODO: evaluate this polynomial.
	// Hint: The time complexity of eval() must be order O(m),
	// where m is the largest degree of the polynomial. Notice
	// that the function SLinkedList.get(index) method is O(m),
	// so if your eval() method were to call the get(index)
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x) {
		/**** ADD CODE HERE ****/
		
		BigInteger result = new BigInteger("0");
		
		if(polynomial.isEmpty()) {
			return result;
		}
		
		else {
			int prevexp = polynomial.get(0).getExponent();
			for(Term currentTerm : polynomial) {
				BigInteger coeff = currentTerm.getCoefficient();
				int exp = currentTerm.getExponent();
				result = result.multiply(x.pow(prevexp-exp)).add(coeff);
				prevexp = exp;
			}
			result = result.multiply(x.pow(prevexp));
		
			return result;
			
		}
		
	}

	// Checks if this polynomial is same as the polynomial in the argument
	public boolean checkEqual(Polynomial p) {
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;

		int index = 0;
		for (Term term0 : polynomial) {
			Term term1 = p.getTerm(index);

			if (term0.getExponent() != term1.getExponent()
					|| term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
				return false;

			index++;
		}
		return true;
	}

	// This method blindly adds a term to the end of LinkedList polynomial.
	// Avoid using this method in your implementation as it is only used for
	// testing.
	public void addTermLast(Term t) {
		polynomial.addLast(t);
	}

	// This is used for testing multiplyTerm
	public void multiplyTermTest(Term t) {
		multiplyTerm(t);
	}

	@Override
	public String toString() {
		if (polynomial.size() == 0)
			return "0";
		return polynomial.toString();
	}
}
