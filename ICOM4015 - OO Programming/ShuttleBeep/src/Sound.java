import java.io.*;
import javax.sound.sampled.*;

/**
 * The Sound class set different sounds for each discover or miss in the game
 * @author Eduardo && David
 */
public class Sound implements Serializable {

	private static final long serialVersionUID = -8207003960509455858L;
	public Clip clip; // This object get the audio 

	/**
	 * This method get the sound from a file and place it on clip object
	 * @param soundFileName the name of the sound
	 */
	public void soundEffect(String soundFileName) {

		try {
			File soundFile = new File(soundFileName);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

			clip = AudioSystem.getClip();
			clip.open(audioIn);     
		} 
		catch(IOException error) {

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method start and stop the sound
	 */
	public void play() {

		// Stop the player if it is still running
		if (clip.isRunning()) {
			clip.stop(); 
		}  

		clip.setFramePosition(0); // rewind to the beginning
		clip.start();     // Start playing
	}
}



