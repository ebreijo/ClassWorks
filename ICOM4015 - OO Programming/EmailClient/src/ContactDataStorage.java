import java.util.HashMap;
import java.util.Map;


/**
 * IMPORTANT: This class is to be implemented by students!
 * This class describes an Email Contact that stores contacts
 * @author Eduardo
 */
public class ContactDataStorage implements StorageDataStructure<EmailContact> {

	private Map<String, EmailContact> contactData;
	
	/**
	 * Construct a HashMap with String key and EmailContact value
	 */
	public ContactDataStorage() {
		contactData = new HashMap<String, EmailContact>(200);
	}

	/**
	 * Adds a new EmailContact object to the storage. If a key already exists in the storage, the EmailContact object
	 * with that key is replaced by the given one.
	 * @param key The key of the EmailContact object to be stored
	 * @param obj The EmailContact object to be stored.
	 */
	@Override
	public void add(String key, EmailContact obj) {
		// TODO Auto-generated method stub
		contactData.put(key, obj);
	}

	/**
	 * Removes the EmailContact object with the specified key from the storage.
	 * @param key The key of the EmailContact object to be removed.
	 * @return True if the EmailContact object was successfully removed, false otherwise.
	 */
	@Override
	public boolean remove(String key) {
		// TODO Auto-generated method stub
		if (!isEmpty()) {
			contactData.remove(key);
			return true;
		} 
		else {
			return false;
		}
	}

	/**
	 * Returns the EmailContact object with the given key in the storage.
	 * @param key The key of the EmailContact object to be returned.
	 * @return The EmailContact object that has the key specified.
	 */
	@Override
	public EmailContact get(String key) {
		// TODO Auto-generated method stub
		return contactData.get(key);
	}

	/**
	 * Checks whether an EmailContact object with the given key exists in the storage.
	 * @param key The key of the EmailContact object to be checked.
	 * @return True if the EmailContact object is in the storage, false otherwise.
	 */
	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return contactData.containsKey(key);
	}

	/**
	 * Loads the storage from a given String array, that could be loaded from a file.
	 * @param file The String array containing a storage object.
	 * @return True if the String array contained a proper storage object, false otherwise.
	 */
	@Override
	public boolean loadFromFileFormat(String[] file) {
		// TODO Auto-generated method stub
		clear();

		try {
			for (int i = 0; i < file.length; i++) {
				String [] strings = file[i].split(".,@.@,.");
				strings[0] = strings[0].substring(6).trim(); 
				strings[1] = strings[1].substring(7).trim();
				strings[2] = strings[2].substring(7).trim();
				strings[3] = strings[3].substring(9).trim();
				strings[4] = strings[4].substring(6).trim();

				EmailContact contact = new EmailContact(strings[0], strings[1], strings[2], strings[3], strings[4]);	
				contactData.put("" + (i + 1), contact);
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
		// TODO Auto-generated method stub
		String[] str = new String[size()];
		
		for (int i = 1; i <= contactData.size(); i++) {
			EmailContact contact = contactData.get("" + i);
			
			String name = contact.getContactName();
			String email = contact.getContactEmail();
			String phone = contact.getContactPhoneNumber();
			String address = contact.getContactAddress();
			String note = contact.getContactNote();
			
			str[i-1] = "Name: " + name + ".,@.@,. " + "Email: " + email + ".,@.@,. " + "Phone: " + phone + ".,@.@,. " 
						+ "Address: " + address + ".,@.@,. " + "Note: " + note + "\n";
		}
		
		return str;
	}
	
	/**
	 * Get an array of all the objects of type EmailContact being stored in the storage.
	 * @return Array of objects stored.
	 */
	@Override
	public EmailContact[] getAllValues() {
		// TODO Auto-generated method stub
		EmailContact [] contacts = new EmailContact[contactData.size()];
		String[] keys = new String[contactData.size()];
		int i = 0;
		
		for (Map.Entry<String, EmailContact> m : contactData.entrySet()) {
			keys[i] = m.getKey();
			contacts[i] = m.getValue();
			i++;
		}
		return contacts;
	}

	/**
	 * Get the actual size of the storage. 
	 * @return The size of the storage.
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return contactData.size();
	}

	/**
	 * Checks whether the data storage is empty or not.
	 * @return True if it's empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return contactData.isEmpty();
	}

	/**
	 * Empties the Data Storage.
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		contactData.clear();
	}
	
}
