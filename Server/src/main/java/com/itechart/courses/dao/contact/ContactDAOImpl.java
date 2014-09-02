package com.itechart.courses.dao.contact;

import com.itechart.courses.entity.Contact;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer createContact(Contact contact) {
        if (contact == null) {
            throw new NullPointerException("contact is null");
        }
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(contact);
            transaction.commit();
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }

    @SuppressWarnings("JpaQlInspection")
    public boolean deleteContact(int id) {
        boolean result = false;
        Session session = null;
        Transaction transaction = null;
        Contact contact = null;
        String hql = "update Contact set isDelete = :isDelete where id = :id";
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setBoolean("isDelete", true);
            query.setInteger("id", id);
            query.executeUpdate();
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

    public Contact readContact(int id) {
        Contact contact = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            contact = (Contact) session.get(Contact.class, id);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return contact;
    }

    @SuppressWarnings("JpaQlInspection")
    public List<Contact> readAllContacts(){
        Session session = null;
        List<Contact> result = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from Contact where isDelete = :isDelete");
            query.setBoolean("isDelete", false);
            result = query.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public void updateContact(Contact contact) {
        if (contact == null) {
            throw new NullPointerException("contact is null");
        }

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(contact);
            transaction.commit();
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
