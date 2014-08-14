package bl.dao.contact;

import entity.Contact;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ContactDAOImpl implements ContactDAO {
    private static ContactDAOImpl ourInstance = new ContactDAOImpl();
    private SessionFactory factory;

    private ContactDAOImpl(){
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static ContactDAOImpl getInstance() {
        return ourInstance;
    }

    public Integer createContact(Contact contact){
        if (contact == null){
            throw new NullPointerException("contact is null");
        }

        Integer id = null;
        Session session = null;

        try {
            session = factory.openSession();
            session.beginTransaction();
            id = (Integer) session.save(contact);
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }

    public boolean deleteContact(int id){
        boolean result = false;
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            Contact contact = (Contact) session.get(Contact.class, id);
            if (contact != null){
                session.delete(contact);
                result = true;
            }
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public Contact readContact(int id){
        Contact contact = null;
        Session session = null;
        try {
            session = factory.openSession();
            contact = (Contact) session.get(Contact.class, id);
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return contact;
    }

    public void updateContact(Contact contact){
        if (contact == null){
            throw new NullPointerException("contact is null");
        }

        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(contact);
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
