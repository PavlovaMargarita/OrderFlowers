package com.itechart.courses.dao.orderHistory;

import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.OrderHistory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Александр on 14.08.2014.
 */

@Repository
@Transactional
public class OrderHistoryDAOImpl implements OrderHistoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer createOrderHistory(OrderHistory orderHistory) {
        Integer id = null;
        Session session = null;
        if (orderHistory == null){
            throw new NullPointerException("orderHistory is null");
        }
        session = sessionFactory.getCurrentSession();
        id = (Integer) session.save(orderHistory);
        return id;
    }

    @Override
    public OrderHistory readOrderHistory(int id) {
        Session session = sessionFactory.getCurrentSession();
        OrderHistory orderHistory = (OrderHistory) session.get(OrderHistory.class, id);
        return orderHistory;
    }

    @Override
    public void updateOrderHistory(OrderHistory orderHistory) {
        Session session = null;
        if (orderHistory == null){
            throw new NullPointerException("orderHistory is null");
        }
        session = sessionFactory.getCurrentSession();
        session.update(orderHistory);
    }

    @Override
    public List readOrderHistory(Order order) {
        List<OrderHistory> result = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from OrderHistory where order = :order");
        query.setParameter("order", order);
        result = query.list();
        return result;
    }
}
