package com.itechart.courses.dao.contact;

import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.entity.Contact;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.sql.Date;
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
    
    public List<Contact> searchContact(ContactSearchDTO parameters){
        if (parameters == null){
            throw new NullPointerException("parameters is null");
        }
        Session session = null;
        List<Contact> contacts = null;
        StringBuilder builder = new StringBuilder();
        String surname = parameters.getSurname();
        String name = parameters.getName();
        String patronymic = parameters.getPatronymic();
        Date lowerDateOfBirth = parameters.getLowerDateOfBirth();
        Date upperDateOfBirth = parameters.getUpperDateOfBirth();
        String city = parameters.getCity();
        String street = parameters.getStreet();
        Integer home = parameters.getHome();
        Integer flat = parameters.getFlat();

        if (surname != null){
            builder.append("contact.surname = :surname");
        }
        if (name != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.name = :name");
        }
        if (patronymic != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.patronymic = :patronymic");
        }

        if (lowerDateOfBirth != null && upperDateOfBirth != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth BETWEEN :lowerDateOfBirth AND :upperDateOfBirth");
        }
        else if (lowerDateOfBirth != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth >= :lowerDateOfBirth");
        }
        else if (upperDateOfBirth != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth <= :upperDateOfBirth");
        }

        if (city != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.city = :city");
        }
        if (street != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.street = :street");
        }
        if (home != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.home = :home");
        }
        if (flat != null){
            if (builder.length() != 0){
                builder.append(" AND ");
            }
            builder.append("contact.flat = :flat");
        }
        builder.insert(0, "from Contact contact where ");
        builder.append(" AND contact.isDelete = false");

        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery(builder.toString());
            if (surname != null){
                query.setString("surname", surname);
            }
            if (name != null){
                query.setString("name", name);
            }
            if (patronymic != null){
                query.setString("patronymic", patronymic);
            }

            if (lowerDateOfBirth != null && upperDateOfBirth != null){
                query.setDate("lowerDateOfBirth", lowerDateOfBirth);
                query.setDate("upperDateOfBirth", upperDateOfBirth);
            }
            else if (lowerDateOfBirth != null){
                query.setDate("lowerDateOfBirth", lowerDateOfBirth);
            }
            else if (upperDateOfBirth != null){
                query.setDate("upperDateOfBirth", upperDateOfBirth);
            }

            if (city != null){
                query.setString("city", city);
            }
            if (street != null){
                query.setString("street", street);
            }
            if (home != null){
                query.setInteger("home", home);
            }
            if (flat != null){
                query.setInteger("flat", flat);
            }
            contacts = (List<Contact>) query.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return contacts;
    }
}
