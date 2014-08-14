package bl.dao.contact;

import entity.Contact;


public interface ContactDAO {
    public Integer createContact(Contact contact);
    public boolean deleteContact(int id);
    public Contact readContact(int id);
    public void updateContact(Contact contact);
}
