import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


/**
 * IMPORTANT: THIS CLASS IS TO BE IMPLEMENTED BY STUDENTS!
 * This class describes some kind of database in which email messages are stored.
 * @author Eduardo
 *
 */
public class EmailDataStorage implements StorageDataStructure<Message> {

	private Map<String, Message> emailData;
	private EmailServer server;

	/**
	 * Construct a HashMap with a string key and message value.
	 * @param server The server of EmailServer class
	 */
	public EmailDataStorage(EmailServer server) {
		emailData = new HashMap<String, Message>(200);
		this.server = server;
	}

	/**
	 * Adds a new Message to the storage. If a key already exists in the storage, the Message
	 * with that key is replaced by the given one.
	 * @param key The key of the Message to be stored
	 * @param obj The Messsage to be stored.
	 */
	@Override
	public void add(String key, Message obj) {
		// TODO Auto-generated method stub
		emailData.put(key, obj);
	}

	/**
	 * Removes the Message with the specified key from the storage.
	 * @param key The key of the Message to be removed.
	 * @return True if the Message was successfully removed, false otherwise.
	 */
	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		if (!isEmpty()) {
			emailData.remove(key);
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Returns the Message with the given key in the storage.
	 * @param key The key of the Message to be returned.
	 * @return The Message that has the key specified.
	 */
	@Override
	public Message get(String key) {
		// TODO Auto-generated method stub
		return emailData.get(key);
	}

	/**
	 * Checks whether a Message with the given key exists in the storage.
	 * @param key The key of the Message to be checked.
	 * @return True if the Message is in the storage, false otherwise.
	 */
	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return emailData.containsKey(key);
	}
	
	/**
	 * Loads the storage from a given String array, that could be loaded from a file.
	 * @param file The String array containing a storage Message.
	 * @return True if the String array contained a proper storage Message, false otherwise.
	 */
	@Override
	public boolean loadFromFileFormat(String[] file) {
		clear();
		
		try {
			for (int i = 0; i < file.length; i++) {
				String[] strings = file[i].split(".,@.@,.");
				
				MimeMessage m = new MimeMessage(server.getSession());
				Message message = null;
				try {
					m.setRecipient(RecipientType.TO, new InternetAddress(strings[0]));
					m.setFrom(new InternetAddress(strings[1]));
					m.setSubject(strings[2]);
					m.setContent(strings[3], "text/html");
					message = m;
					emailData.put("" + i, message);
					
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Format the data in a String array by describing the data storage that can be saved to a file. 
	 * @return The String array containing the data storage.
	 */
	@Override
	public String[] toFileFormat() {
		String [] str = new String[size()];
		
		Address[] recipients = null;
		Address[] from = null;
		String subject = null;
		String content = null;		
		
		for (int i = 0; i < size(); i++) {
			
			Message mes = emailData.get("" + i);
			
			try {
				recipients = mes.getAllRecipients();
				from = mes.getFrom();
				subject = mes.getSubject();
				content = getText(mes);
			} catch (MessagingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			str[i] = recipients[0] + ".,@.@,. " + from[0] + ".,@.@,. " + subject + ".,@.@,. " +  content + ".,@.@,. ";
			
		}
		return str;
	}
	
	/**
     * Return the primary text content of the message.
     */
	
    public String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

	/**
	 * Get an array of all the object of type Message being stored in the storage.
	 * @return Array of Message stored.
	 */
	@Override
	public Message[] getAllValues() {
		// TODO Auto-generated method stub
		Message [] messages = new Message[size()];
		
		int i = 0;
		for (Map.Entry<String, Message> m : emailData.entrySet()) {
			messages[i] = m.getValue();
			i++;
		}
		return messages;
	}

	/**
	 * Get the actual size of the storage. 
	 * @return The size of the storage.
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return emailData.size();
	}

	/**
	 * Checks whether the data storage is empty or not.
	 * @return True if it's empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return emailData.isEmpty();
	}

	/**
	 * Empties the Data Storage.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		emailData.clear();
	}
}
