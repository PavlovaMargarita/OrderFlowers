package com.itechart.courses.dao.order;

import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */

@Repository
@Transactional
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
    public List<Order> readAllOrders() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
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
    public List<Order> searchOrder(OrderSearchDTO parameters) {
        StringBuilder builder = new StringBuilder();
        String customerSurname = parameters.getCustomerSurname();
        String recipientSurname = parameters.getRecipientSurname();
        Date lowerOrderDate = parameters.getLowerOrderDate();
        Date upperOrderDate = parameters.getUpperOrderDate();

        if (lowerOrderDate != null && upperOrderDate != null) {
            builder.append("history.changeDate between :lowerOrderDate AND :upperOrderDate");
        } else if (lowerOrderDate != null) {
            builder.append("history.changeDate >= :lowerOrderDate");
        } else if (upperOrderDate != null) {
            builder.append("history.changeDate <= :upperOrderDate");
        }

        if (customerSurname != null){
            if (builder.length() != 0){
                builder.append(" and ");
            }
            builder.append("history.order.customer.surname = :customerSurname");
        }
        if (recipientSurname != null){
            if (builder.length() != 0){
                builder.append(" and ");
            }
            builder.append("history.order.recipient.surname = :recipientSurname");
        }

        builder.append(" and history.status = :orderStatus");
        builder.insert(0, "select history.order from OrderHistory history where ");
        Query query = sessionFactory.getCurrentSession().createQuery(builder.toString());

        if (customerSurname != null){
            query.setString("customerSurname", customerSurname);
        }
        if (recipientSurname != null){
            query.setString("recipientSurname", recipientSurname);
        }
        if (lowerOrderDate != null){
            query.setDate("lowerOrderDate", lowerOrderDate);
        }
        if (upperOrderDate != null){
            query.setDate("upperOrderDate", upperOrderDate);
        }
        query.setString("orderStatus", OrderStatusEnum.NEW.toString());
        return query.list();
    }
}
