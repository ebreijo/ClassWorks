
/**
 * IMPORTANT: This class is to be implemented by students!
 * This class describes a contact to be used on the email client. 
 * @author Eduardo
 */
public class EmailContact {


	private String contactName;
	private String contactEmail;
	private String contactPhoneNumber;
	private String contactAddress;
	private String contactNote;

	/**
	 * Construct a contact with a given name, email, phone, address and a note
	 * @param name The name
	 * @param email The email address
	 * @param phone The phone number
	 * @param address The address
	 * @param note A note
	 */
	public EmailContact(String name, String email, String phone, String address, String note) {
		contactName = name;
		contactEmail = email;
		contactPhoneNumber = phone;
		contactAddress = address;
		contactNote = note;
	}

	/**
	 * Gets the name of the contact
	 * @return the name
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Gets the email address of the contact
	 * @return the email
	 */
	public String getContactEmail() {
		return contactEmail;
	}

	/**
	 * Gets the phone number of the contact
	 * @return the phone number
	 */
	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	/**
	 * Gets the address of the contact
	 * @return the address
	 */
	public String getContactAddress() {
		return contactAddress;
	}

	/**
	 * Get a note of the contact
	 * @return a note of the contact
	 */
	public String getContactNote() {
		return contactNote;
	}
}


