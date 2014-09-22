package com.itechart.courses.dao.contact;

import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.entity.Contact;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

        if (surname != null) {
            builder.append("contact.surname = :surname");
        }
        if (name != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.name = :name");
        }
        if (patronymic != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.patronymic = :patronymic");
        }

        if (lowerDateOfBirth != null && upperDateOfBirth != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth BETWEEN :lowerDateOfBirth AND :upperDateOfBirth");
        } else if (lowerDateOfBirth != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth >= :lowerDateOfBirth");
        } else if (upperDateOfBirth != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.dateOfBirth <= :upperDateOfBirth");
        }

        if (city != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.city = :city");
        }
        if (street != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.street = :street");
        }
        if (home != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.home = :home");
        }
        if (flat != null) {
            if (builder.length() != 0) {
                builder.append(" AND ");
            }
            builder.append("contact.flat = :flat");
        }
        builder.insert(0, "from Contact contact where ");
        builder.append(" AND contact.isDelete = false");

        session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(builder.toString());
        if (surname != null) {
            query.setString("surname", surname);
        }
        if (name != null) {
            query.setString("name", name);
        }
        if (patronymic != null) {
            query.setString("patronymic", patronymic);
        }

        if (lowerDateOfBirth != null && upperDateOfBirth != null) {
            query.setDate("lowerDateOfBirth", lowerDateOfBirth);
            query.setDate("upperDateOfBirth", upperDateOfBirth);
        } else if (lowerDateOfBirth != null) {
            query.setDate("lowerDateOfBirth", lowerDateOfBirth);
        } else if (upperDateOfBirth != null) {
            query.setDate("upperDateOfBirth", upperDateOfBirth);
        }

        if (city != null) {
            query.setString("city", city);
        }
        if (street != null) {
            query.setString("street", street);
        }
        if (home != null) {
            query.setInteger("home", home);
        }
        if (flat != null) {
            query.setInteger("flat", flat);
        }
        query.setFirstResult(first);
        query.setMaxResults(count);
        contacts = (List<Contact>) query.list();
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

        String surname = parameters.getSurname();
        String name = parameters.getName();
        String patronymic = parameters.getPatronymic();
        Date lowerDateOfBirth = parameters.getLowerDateOfBirth();
        Date upperDateOfBirth = parameters.getUpperDateOfBirth();
        String city = parameters.getCity();
        String street = parameters.getStreet();
        Integer home = parameters.getHome();
        Integer flat = parameters.getFlat();

        addRestrictionIfNotNull(criteria, "surname", surname);
        addRestrictionIfNotNull(criteria, "name", name);
        addRestrictionIfNotNull(criteria, "patronymic", patronymic);
        if(lowerDateOfBirth != null && upperDateOfBirth != null){
            criteria.add(Restrictions.between("dateOfBirth", lowerDateOfBirth, upperDateOfBirth));
        } else if(lowerDateOfBirth != null){
            criteria.add(Restrictions.ge("dateOfBirth", lowerDateOfBirth));
        } else if(upperDateOfBirth != null){
            criteria.add(Restrictions.le("dateOfBirth", upperDateOfBirth));
        }
        addRestrictionIfNotNull(criteria, "city", city);
        addRestrictionIfNotNull(criteria, "street", street);
        addRestrictionIfNotNull(criteria, "home", home);
        addRestrictionIfNotNull(criteria, "flat", flat);
        criteria.add(Restrictions.eq("isDelete", false));
        int totalCount = ((Number) criteria.uniqueResult()).intValue();

        return totalCount;
    }

    private void addRestrictionIfNotNull(Criteria criteria, String propertyName, Object value) {
        if (value != null) {
            criteria.add(Restrictions.eq(propertyName, value));
        }
    }
}
