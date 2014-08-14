package bl.dao.order;

import entity.Contact;
import entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Margarita on 14.08.2014.
 */
public class OrderDAOImpl implements OrderDAO {
    private static OrderDAOImpl ourInstance = new OrderDAOImpl();
    private SessionFactory factory;

    public static OrderDAOImpl getInstance() {
        return ourInstance;
    }

    private OrderDAOImpl() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public Integer createOrder(Order order) {
        if (order == null){
            throw new NullPointerException("order is null");
        }

        Integer id = null;
        Session session = null;

        try {
            session = factory.openSession();
            session.beginTransaction();
            id = (Integer) session.save(order);
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }
    public boolean deleteOrder(int id) {
        boolean result = false;
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            Order order = (Order) session.get(Order.class, id);
            if (order != null){
                session.delete(order);
                result = true;
            }
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }
    public Order readOrder(int id) {
        Order order = null;
        Session session = null;
        try {
            session = factory.openSession();
            order = (Order) session.get(Order.class, id);
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return order;
    }
    public void updateOrder(Order order) {
        if (order == null){
            throw new NullPointerException("contact is null");
        }

        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
