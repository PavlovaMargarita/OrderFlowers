package bl.dao.contact;

import entity.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


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
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(contact);
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return id;
    }

    public boolean deleteContact(int id){
        boolean result = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Contact contact = (Contact) session.get(Contact.class, id);
            if (contact != null){
                session.delete(contact);
                result = true;
            }
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
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


    public List<Contact> readAllContacts() {
        List<Contact> contacts = null;
        Session session = null;
        try {
            session = factory.openSession();
            Criteria criteria = session.createCriteria(Contact.class);
            contacts = criteria.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return contacts;
    }

    public void updateContact(Contact contact){
        if (contact == null){
            throw new NullPointerException("contact is null");
        }

        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(contact);
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
