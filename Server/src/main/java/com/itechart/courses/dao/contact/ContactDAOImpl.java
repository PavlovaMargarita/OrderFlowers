package com.itechart.courses.dao.contact;

import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.entity.Contact;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer createContact(Contact contact) {
        Integer id = null;
        Session session = null;
        if (contact == null) {
            throw new NullPointerException("contact is null");
        }
        session = sessionFactory.getCurrentSession();
        id = (Integer) session.save(contact);
        return id;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public void deleteContact(int id) {
        Query query = null;
        Session session = null;
        String hql = "update Contact set isDelete = :isDelete where id = :id";
        session = sessionFactory.getCurrentSession();
        query = session.createQuery(hql);
        query.setBoolean("isDelete", true);
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public Contact readContact(int id) {
        Session session = sessionFactory.getCurrentSession();
        Contact contact = (Contact) session.get(Contact.class, id);
        return contact;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<Contact> readAllContacts() {
        List<Contact> result = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Contact where isDelete = :isDelete");
        query.setBoolean("isDelete", false);
        result = query.list();
        return result;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<Contact> readContacts(int first, int count) {
        List<Contact> result = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Contact where isDelete = :isDelete");
        query.setFirstResult(first);
        query.setMaxResults(count);
        query.setBoolean("isDelete", false);
        result = query.list();
        return result;
    }

    @Override
    public void updateContact(Contact contact) {
        Session session = null;
        if (contact == null) {
            throw new NullPointerException("contact is null");
        }
        session = sessionFactory.getCurrentSession();
        session.update(contact);
    }

    @Override
    public List<Contact> searchContactByDateOfBirth(int month, int day) {
        List<Contact> contacts = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Contact contact where DAYOFMONTH(contact.dateOfBirth) = :day and MONTH(contact.dateOfBirth) = :month");
        query.setInteger("month", month);
        query.setInteger("day", day);
        contacts = query.list();
        return contacts;
    }


    @Override
    public List<Contact> searchContact(ContactSearchDTO parameters, int first, int count) {
        if (parameters == null) {
            throw new NullPointerException("parameters is null");
        }
        List<Contact> contacts;
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Contact.class);
        buildContactSearchCriteria(criteria, parameters);
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        contacts = (List<Contact>) criteria.list();
        return contacts;
    }

    @Override
    public List<Contact> searchContact(ContactSearchDTO parameters) {
        if (parameters == null) {
            throw new NullPointerException("parameters is null");
        }
        List<Contact> contacts;
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Contact.class);
        buildContactSearchCriteria(criteria, parameters);
        contacts = (List<Contact>) criteria.list();
        return contacts;
    }

    @Override
    public int getContactCount() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Contact.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("isDelete", false));
        int totalCount = ((Number) criteria.uniqueResult()).intValue();
        return totalCount;
    }

    @Override
    public int getContactCount(ContactSearchDTO parameters) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Contact.class);
        criteria.setProjection(Projections.rowCount());
        buildContactSearchCriteria(criteria, parameters);
        int totalCount = ((Number) criteria.uniqueResult()).intValue();
        return totalCount;
    }

    private void buildContactSearchCriteria(Criteria criteria, ContactSearchDTO parameters){
        String surname = parameters.getSurname();
        if (surname != null && (surname = surname.trim()).isEmpty()){
            surname = null;
        }
        String name = parameters.getName();
        if (name != null && (name = name.trim()).isEmpty()){
            name = null;
        }
        String patronymic = parameters.getPatronymic();
        if (patronymic != null && (patronymic = patronymic.trim()).isEmpty()){
            patronymic = null;
        }
        Date lowerDateOfBirth = parameters.getLowerDateOfBirth();
        Date upperDateOfBirth = parameters.getUpperDateOfBirth();
        String city = parameters.getCity();
        if (city != null && (city = city.trim()).isEmpty()){
            city = null;
        }
        String street = parameters.getStreet();
        if (street != null && (street = street.trim()).isEmpty()){
            street = null;
        }
        Integer home = parameters.getHome();
        Integer flat = parameters.getFlat();

        addStringRestrictionIfNotNull(criteria, "surname", surname);
        addStringRestrictionIfNotNull(criteria, "name", name);
        addStringRestrictionIfNotNull(criteria, "patronymic", patronymic);
        if(lowerDateOfBirth != null && upperDateOfBirth != null){
            criteria.add(Restrictions.between("dateOfBirth", lowerDateOfBirth, upperDateOfBirth));
        } else if(lowerDateOfBirth != null){
            criteria.add(Restrictions.ge("dateOfBirth", lowerDateOfBirth));
        } else if(upperDateOfBirth != null){
            criteria.add(Restrictions.le("dateOfBirth", upperDateOfBirth));
        }
        addStringRestrictionIfNotNull(criteria, "city", city);
        addStringRestrictionIfNotNull(criteria, "street", street);
        addIntegerRestrictionIfNotNull(criteria, "home", home);
        addIntegerRestrictionIfNotNull(criteria, "flat", flat);
        criteria.add(Restrictions.eq("isDelete", false));
    }

    private void addStringRestrictionIfNotNull(Criteria criteria, String propertyName, String value) {
        if (value != null) {
            criteria.add(Restrictions.like(propertyName, value, MatchMode.ANYWHERE));
        }
    }

    private void addIntegerRestrictionIfNotNull(Criteria criteria, String propertyName, Integer value) {
        if (value != null) {
            criteria.add(Restrictions.eq(propertyName, value));
        }
    }

}
