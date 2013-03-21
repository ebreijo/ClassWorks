import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * The Score class save the score of each player in a document
 * @author Eduardo && David
 */
public class Score  {
	private static String higherScore; // Save the higher score
	private static String name = "Rofongo"; // The name of the robot
	private FileWriter output; // To write the scores in a .txt document
	ArrayList<String> parser = new ArrayList<String>();

	
	public Score() {}
	
	/**
	 * This method submit or write the score for each player in a document
	 * @param playerName the name of the player
	 * @param i the index 
	 */
	public void submitScore(String playerName, int i) {
		try {
			output = new FileWriter("highscore.txt",true);
			output.write(String.valueOf(i)); 
			output.append(" "+playerName);
			output.append("\n");
			output.close();
		} catch (IOException e) {}}
	
	/**
	 * This method get the score for each player
	 * @return
	 */
	public String getScore() {
		return higherScore;
	}
	
	/**
	 * This method read the file where the score was saved.
	 */
	public void readFile() {
		//parser.clear();
		Scanner reader = null;
		try {
			reader = new Scanner(new FileReader("highscore.txt"));
		}
		catch(Exception e) {
			System.out.println("Not found");
		}
		while(reader.hasNextLine()) 
			parser.add(reader.nextLine());
		
		Collections.sort(parser);
		
		higherScore = parser.get(0);
		
	//	for(int i = 0; i < 4; i++) {
		//	System.out.println(parser.get(i));
		//higherScore += parser.get(i)+"\n";
	//}
		
	}
	
	/**
	 * Get the name of the player
	 * @return the player name
	 */
	public String getName() {
		return name;
	}
	
}



