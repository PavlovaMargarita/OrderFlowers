package com.itechart.courses.dao.order;

import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.launcher.resources.launcher_it;

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
    public List<Order> readAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums) {
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
        return query.list();
    }

    @Override
    public List<Order> searchOrder(OrderSearchDTO parameters) {
        StringBuilder builder = new StringBuilder();
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
            builder.append("order.date between :lowerOrderDate AND :upperOrderDate");
        } else if (lowerOrderDate != null) {
            builder.append("order.date >= :lowerOrderDate");
        } else if (upperOrderDate != null) {
            builder.append("order.date <= :upperOrderDate");
        }

        if (customerSurname != null){
            if (builder.length() != 0){
                builder.append(" and ");
            }
            builder.append("order.customer.surname LIKE :customerSurname");
        }
        if (recipientSurname != null){
            if (builder.length() != 0){
                builder.append(" and ");
            }
            builder.append("order.recipient.surname LIKE :recipientSurname");
        }
        builder.insert(0, "from Order order where ");

        Query query = sessionFactory.getCurrentSession().createQuery(builder.toString());
        if (customerSurname != null){
            query.setString("customerSurname", "%" + customerSurname + "%");
        }
        if (recipientSurname != null){
            query.setString("recipientSurname", "%" + recipientSurname + "%");
        }
        if (lowerOrderDate != null){
            query.setDate("lowerOrderDate", lowerOrderDate);
        }
        if (upperOrderDate != null){
            query.setDate("upperOrderDate", upperOrderDate);
        }
        return query.list();
    }
}
