
public class testScore {
	public static void main(String[]args) {
		Score s = new Score();
		s.submitScore("David", 69);
		s.submitScore("David", 71);	
		s.submitScore("David", 70);
		s.submitScore("David", 90);
		s.submitScore("David", 69);
		s.submitScore("David", 91);
		s.readFile();
	}
}