import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JFrame;

/**
 * The Runner class initialize every object. Also open/read write/close files 
 * that contains email contacts and messages.
 * @author Eduardo
 */

public class Runner {

	private static EmailDataStorage emailStorage;
	private static ContactDataStorage contactStorage;
	private static EmailServer server;
	private static Properties accountProperties;
	private static JFrame frame;

	/**
	 * THIS METHOD NEEDS TO BE COMPLETED
	 * Static method that will be used to create a new email account if there's no present.
	 */
	public static void createAccountProperties() {
		accountProperties = EmailClientWindow.showNewAccountWindow();
		// If accountProperties equals null, it means the "Cancel" button was clicked
		if(accountProperties == null) {
			System.exit(0);
		}
		//TODO Add code that we need to save the accountProperties to a file...

		try {
			FileWriter output = new FileWriter("AccountProperties.dat");
			accountProperties.store(output, null);
			output.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Here we need to check, first than anything else, that there is an AccountProperties.dat
		// file. If it doesn't exists, we need to create it. You can use the previous method to do
		// so.

		// Also, implement here the code to check if EmailDataStorage.dat and ContactDataStorage.dat exists.
		// If they don't exist, we need to fetch emails from server and save them to the storage. If
		// there are more than 200 email messages in the server, we shall fetch and store only 200.
		// Otherwise, we only store as many emails as are present in the server.
		// For the contact storage, we will only store one dummy contact.

		//TODO Add here the code for checking if required files to read exist. Otherwise, we need
		// to create them following the previous instructions.

		// Once that code has been added we proceed to make the frame and pass it on the readed
		// fields.

		
		File fileProperties = new File("AccountProperties.dat");
		FileReader input = null;

		/**
		 * Create a new account properties if file doesn't exist or load 
		 * the account properties if the file already exist.
		 */
		if (!fileProperties.exists()) {
			createAccountProperties();
		}
		else {
			try {

				try {
					accountProperties = new Properties();

					input = new FileReader("AccountProperties.dat");
					accountProperties.load(input);
				}
				finally {
					input.close();
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Initialize server properties
		 */
		try {
			server = new EmailServer(accountProperties.getProperty("username"), accountProperties.getProperty("password"), 
					                 accountProperties.getProperty("incomingHost"), accountProperties.getProperty("outgoingHost"));
			server.getInboxFromServer();
		} catch (Exception e3) {
			e3.printStackTrace();
		}


		emailStorage = new EmailDataStorage(server);
		contactStorage = new ContactDataStorage();


		File emailDataFile = new File("EmailDataStorage.dat");
		File contactDataFile = new File("ContactDataStorage.dat");

		/**
		 * Create a file to save email if doesn't exist and fetch email
		 * from server or read the file with email stored if already exist. 
		 */
		if (!emailDataFile.exists()) {
			try {
				emailDataFile.createNewFile();
			
				int messageAmount;

				try {

					Message [] m = server.getAllMessages();

					if (server.getMessageCount() > 200) {
						messageAmount = 200;
					} else {
						messageAmount = server.getMessageCount();
					}

					for (int i = 0; i < messageAmount; i++) {
						emailStorage.add("" + i, m[i]);
					}
				} catch (MessagingException e2) {
					e2.printStackTrace(); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {

			try {
				FileInputStream fileIn = new FileInputStream("EmailDataStorage.dat");
				ObjectInputStream in = new ObjectInputStream(fileIn);			
				emailStorage.loadFromFileFormat((String[]) in.readObject());
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		/**
		 * Create a file to save contacts if doesn't exist and also create a dummy 
		 * contact or read the file with contacts stored if the file already exist. 
		 */
		if (!contactDataFile.exists()) {
			try {
				contactDataFile.createNewFile();
				EmailContact c1 = new EmailContact("Trolea", "trolea.telano@upr.edu", "", "", "");
				contactStorage.add("1", c1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else {
			try {
				Scanner in = new Scanner(new File("ContactDataStorage.dat"));
				ArrayList<String> contactList = new ArrayList<String>();
				
				while (in.hasNextLine()) {
					contactList.add(in.nextLine());
					in.nextLine();
				}
				String [] contact = new String[contactList.size()];

				for (int i = 0; i < contact.length; i++) { 
					contact[i] = contactList.get(i);
				}

				contactStorage.loadFromFileFormat(contact);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		frame = new JFrame();
		frame.setSize(900,600);					// You can change this size if you wish to do so.
		frame.setTitle("Simple Email Client");	// You can change this name by your preference.
		frame.setResizable(false);				// This should remain like this.
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		EmailClientWindow gui = new EmailClientWindow(accountProperties, server, emailStorage, contactStorage);

		frame.add(gui.initializeWindow());

		// Here we are adding a WindowListener to the window frame. The WindowListener allows us
		// to do something before we exit the program. In this case, we want to save the email
		// and contact storage in case they were modified at some point in the program (the email
		// data storage by means of the refresh button and the contact data storage by adding
		// contacts). In any case, you should implement this code. What we are doing here is called
		// an internal class that implements only the method windowClosing() since is the one
		// we are interested in.

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent evt) {
				frame.setVisible(false);

				//TODO Add here the rest of the code to save the email and contact data storage
				// to a file.

				try {
					ObjectOutputStream out = null;
					try {
						FileOutputStream fileOut = new FileOutputStream("EmailDataStorage.dat");
						out = new ObjectOutputStream(fileOut);
						out.writeObject(emailStorage.toFileFormat());
					}
					finally {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					PrintWriter outContact = null;
					try {
						outContact = new PrintWriter("ContactDataStorage.dat");
						String [] s = contactStorage.toFileFormat();

						for (int i = 0; i < s.length; i++) {
							outContact.println(s[i]); 
						}
					}
					finally {
						outContact.close(); 
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		frame.setVisible(true);

	}
}
