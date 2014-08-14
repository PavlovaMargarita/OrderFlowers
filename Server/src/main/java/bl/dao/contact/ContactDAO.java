package bl.dao.contact;

import entity.Contact;


public interface ContactDAO {
    public Integer createContact(Contact contact);  //return id(Integer) new contact
    public boolean deleteContact(int id); //if the contact is deleted, the method returns true, otherwise false
    public Contact readContact(int id); //if the contact is not found, returns null
    public void updateContact(Contact contact);
}
