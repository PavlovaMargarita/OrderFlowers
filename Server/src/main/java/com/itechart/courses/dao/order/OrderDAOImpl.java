package com.itechart.courses.dao.order;

import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//import sun.launcher.resources.launcher_it;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */

@Repository
//@Transactional
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Integer createOrder(Order order) {
        Integer id = null;
        Session session = null;
        if (order == null) {
            throw new NullPointerException("order is null");
        }
        session = sessionFactory.getCurrentSession();
        id = (Integer) session.save(order);
        return id;
    }

    @Override
    public Order readOrder(int id) {
        Session session = sessionFactory.getCurrentSession();
        Order order = (Order) session.get(Order.class, id);
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        Session session = null;
        if (order == null) {
            throw new NullPointerException("order is null");
        }
        session = sessionFactory.getCurrentSession();
        session.update(order);
    }

    @Override
    public List readAllOrders(int first, int count) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        List<Order> result = criteria.list();
        return result;
    }

    @Override
    public List<Order> readAllOrder(Contact contact) {
        List<Order> result = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Order where customer = :customer");
        query.setParameter("customer", contact);
        result = query.list();
        return result;
    }


    @Override
    public List<Order> readAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums, int first, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orderStatusEnums.size(); i++){
            if (builder.length() != 0){
                builder.append(" or ");
            }
            builder.append("order.status = :" + orderStatusEnums.get(i).toString());
        }
        builder.insert(0, "from Order order where order.deliveryManager.id = :id or order.receiveManager.id = :id " +
                "or order.handlerManager.id = :id and ");

        Query query = sessionFactory.getCurrentSession().createQuery(builder.toString());
        query.setInteger("id", userId);
        for (int i = 0; i < orderStatusEnums.size(); i++){
            OrderStatusEnum temp = orderStatusEnums.get(i);
            query.setString(temp.toString(), temp.toString());
        }
        query.setFirstResult(first);
        query.setMaxResults(count);
        return query.list();
    }

    @Override
    public List<Order> searchOrder(OrderSearchDTO parameters, int first, int count) {
        List<Order> orders;
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        buildOrderSearchCriteria(criteria, parameters);
        criteria.setFirstResult(first);
        criteria.setMaxResults(count);
        orders = (List<Order>) criteria.list();
        return orders;
    }

    @Override
    public int getOrdersCount() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setProjection(Projections.rowCount());
        int totalCount = ((Number) criteria.uniqueResult()).intValue();
        return totalCount;
    }

    @Override
    public int getOrderCount(OrderSearchDTO parameters) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setProjection(Projections.rowCount());
        buildOrderSearchCriteria(criteria, parameters);
        int totalCount = ((Number) criteria.uniqueResult()).intValue();
        return totalCount;
    }

    @Override
    public int getOrdersCount(int userId, List<OrderStatusEnum> orderStatusEnums) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < orderStatusEnums.size(); i++){
            if (builder.length() != 0){
                builder.append(" or ");
            }
            builder.append("order.status = :" + orderStatusEnums.get(i).toString());
        }
        builder.insert(0, "Select Count(*) from Order order where order.deliveryManager.id = :id or order.receiveManager.id = :id " +
                "or order.handlerManager.id = :id and ");

        Query query = sessionFactory.getCurrentSession().createQuery(builder.toString());
        query.setInteger("id", userId);
        for (int i = 0; i < orderStatusEnums.size(); i++){
            OrderStatusEnum temp = orderStatusEnums.get(i);
            query.setString(temp.toString(), temp.toString());
        }
        return ((Number)query.uniqueResult()).intValue();
    }

    private void buildOrderSearchCriteria(Criteria criteria, OrderSearchDTO parameters){
        String customerSurname = parameters.getCustomerSurname();
        if (customerSurname != null && (customerSurname = customerSurname.trim()).isEmpty()){
            customerSurname = null;
        }
        String recipientSurname = parameters.getRecipientSurname();
        if (recipientSurname != null && (recipientSurname = recipientSurname.trim()).isEmpty()){
            recipientSurname = null;
        }
        Date lowerOrderDate = parameters.getLowerOrderDate();
        Date upperOrderDate = parameters.getUpperOrderDate();

        if (lowerOrderDate != null && upperOrderDate != null) {
            criteria.add(Restrictions.between("order.date", lowerOrderDate, upperOrderDate));
        } else if (lowerOrderDate != null) {
            criteria.add(Restrictions.ge("order.date", lowerOrderDate));
        } else if (upperOrderDate != null) {
            criteria.add(Restrictions.le("order.date", upperOrderDate));
        }
        addStringRestrictionIfNotNull(criteria, "order.customer.surname", customerSurname);
        addStringRestrictionIfNotNull(criteria, "order.recipient.surname", recipientSurname);

    }

    private void addStringRestrictionIfNotNull(Criteria criteria, String propertyName, String value) {
        if (value != null) {
            criteria.add(Restrictions.like(propertyName, value, MatchMode.ANYWHERE));
        }
    }


}
