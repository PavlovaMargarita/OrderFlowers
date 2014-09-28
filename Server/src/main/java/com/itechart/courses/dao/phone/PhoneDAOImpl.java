package com.itechart.courses.dao.phone;

import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PhoneDAOImpl implements PhoneDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Integer createPhone(Phone phone) {
        Integer id = null;
        Session session = null;
        if (phone == null){
            throw new NullPointerException("phone is null");
        }
        session = sessionFactory.getCurrentSession();
        id = (Integer) session.save(phone);
        return id;
    }

    @Override
    public boolean deletePhone(int id) {
        boolean result = false;
        Session session = sessionFactory.getCurrentSession();
        Phone phone = (Phone) session.get(Phone.class, id);
        if (phone != null){
            session.delete(phone);
            result = true;
        }
        return result;
    }

    @Override
    public Phone readPhone(int id) {
        Session session = sessionFactory.getCurrentSession();
        Phone phone = (Phone) session.get(Phone.class, id);
        return phone;
    }

    @Override
    public void updatePhone(Phone phone) {
        Session session = null;
        if (phone == null){
            throw new NullPointerException("phone is null");
        }
        session = sessionFactory.getCurrentSession();
        session.update(phone);
    }

    @Override
    public List readAllPhones(Contact contact) {
        List<Phone> result = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Phone where owner = :owner");
        query.setParameter("owner", contact);
        result = query.list();
        return result;
    }

}
