package bl.dao.phone;

import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class PhoneDAOImpl implements PhoneDAO {
    private static PhoneDAOImpl ourInstance = new PhoneDAOImpl();
    private SessionFactory factory;

    private PhoneDAOImpl(){
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static PhoneDAOImpl getInstance() {
        return ourInstance;
    }

    public Integer createPhone(Phone phone) {
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (phone == null){
            throw new NullPointerException("phone is null");
        }
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(phone);
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
        return id;
    }

    public boolean deletePhone(int id) {
        boolean result = false;
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Phone phone = (Phone) session.get(Phone.class, id);
            if (phone != null){
                session.delete(phone);
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

    public Phone readPhone(int id) {
        Phone phone = null;
        Session session = null;
        try {
            session = factory.openSession();
            phone = (Phone) session.get(Phone.class, id);
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return phone;
    }

    public void updatePhone(Phone phone) {
        if (phone == null){
            throw new NullPointerException("phone is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(phone);
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
