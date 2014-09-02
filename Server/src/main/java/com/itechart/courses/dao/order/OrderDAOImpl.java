package com.itechart.courses.dao.order;

import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */

@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createOrder(Order order) {
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (order == null) {
            throw new NullPointerException("order is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(order);
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

    public Order readOrder(int id) {
        Order order = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            order = (Order) session.get(Order.class, id);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return order;
    }

    public void updateOrder(Order order) {
        Session session = null;
        Transaction transaction = null;
        if (order == null) {
            throw new NullPointerException("order is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(order);
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

    @Override
    public List<Order> readAllOrders() {
        Session session = null;
        List<Order> result = null;
        try {
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(Order.class);
            result = criteria.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    @Override
    public List<Order> readAllOrder(Contact contact) {
        List<Order> result = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from Order where customer = :customer");
            query.setParameter("customer", contact);
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
