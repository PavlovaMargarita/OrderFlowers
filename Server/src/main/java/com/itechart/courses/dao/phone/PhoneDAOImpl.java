package com.itechart.courses.dao.phone;

import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PhoneDAOImpl implements PhoneDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createPhone(Phone phone) {
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (phone == null){
            throw new NullPointerException("phone is null");
        }
        try {
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
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
            session = sessionFactory.openSession();
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

    @Override
    public List readAllPhones(Contact contact) {
        List<Phone> result = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from Phone where owner = :owner");
            query.setParameter("owner", contact);
            result = query.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

}
