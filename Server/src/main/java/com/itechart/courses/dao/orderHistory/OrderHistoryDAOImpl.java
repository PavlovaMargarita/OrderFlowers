package com.itechart.courses.dao.orderHistory;

import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.OrderHistory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Александр on 14.08.2014.
 */

@Repository
public class OrderHistoryDAOImpl implements OrderHistoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createOrderHistory(OrderHistory orderHistory) {
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (orderHistory == null){
            throw new NullPointerException("orderHistory is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(orderHistory);
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

    public OrderHistory readOrderHistory(int id) {
        OrderHistory orderHistory = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            orderHistory = (OrderHistory) session.get(OrderHistory.class, id);
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return orderHistory;
    }

    public void updateOrderHistory(OrderHistory orderHistory) {
        Session session = null;
        Transaction transaction = null;
        if (orderHistory == null){
            throw new NullPointerException("orderHistory is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(orderHistory);
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
    public List readOrderHistory(Order order) {
        List<OrderHistory> result = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from OrderHistory where order = :order");
            query.setParameter("order", order);
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
