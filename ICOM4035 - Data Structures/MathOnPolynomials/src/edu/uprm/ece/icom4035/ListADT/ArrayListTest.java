package edu.uprm.ece.icom4035.ListADT;

public class ArrayListTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ListFactory<String> factory = new ArrayListFactory<String>();
		List<String> theList = factory.newInstance(); 
		
		theList.add("Ron");
		theList.add("Jil");
		theList.add("Amy");
		theList.add("Ron");
		printList(theList);
		
		theList.add(0, "Apu");
		theList.add(theList.size(), "Xi");
		System.out.println("First element: " + theList.first());
		System.out.println("Last element: " + theList.last());
		printList(theList);
		
		theList.remove("Amy");
		System.out.println("After removing Amy: " + theList.last());
		printList(theList);
		
		theList.removeAll("Ron");
		System.out.println("After removing all Ron: " + theList.last());
		printList(theList);
		
		theList.add("Mel");
		theList.add(1, "Cal");
		printList(theList);
		theList.add("Cal");
		printList(theList);
		System.out.println("First Index of Cal: " + theList.firstIndex("Cal"));
		System.out.println("Last Index of Cal: " + theList.lastIndex("Cal"));
		System.out.println("First Index of Xi: " + theList.firstIndex("Xi"));
		System.out.println("Last Index of Xi: " + theList.lastIndex("Xi"));
		System.out.println("First Index of Li: " + theList.firstIndex("Li"));
		System.out.println("Last Index of Li: " + theList.lastIndex("Li"));
		
		System.out.println("Element at position 2: " + theList.get(2));
		theList.set(2,"Al");
		System.out.println("Element at position 2: " + theList.get(2));
		printList(theList);
		
		theList.clear();
		printList(theList);

	}

	private static void printList(List<String> theList) {
		System.out.println("List size: " + theList.size());
		for (String s : theList) {
			System.out.println(s);
		}
		
	}

}
