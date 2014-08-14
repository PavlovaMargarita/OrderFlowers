package bl.dao.orderHistory;

import entity.OrderHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by Александр on 14.08.2014.
 */
public class OrderHistoryDAOImpl implements OrderHistoryDAO {
    private static OrderHistoryDAOImpl ourInstance = new OrderHistoryDAOImpl();
    private SessionFactory factory;

    private OrderHistoryDAOImpl(){
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static OrderHistoryDAOImpl getInstance() {
        return ourInstance;
    }

    public Integer createOrderHistory(OrderHistory orderHistory) {
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (orderHistory == null){
            throw new NullPointerException("orderHistory is null");
        }
        try {
            session = factory.openSession();
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

    public boolean deleteOrderHistory(int id) {
        boolean result = false;
        Session session = null;
        Transaction transaction = null;
        OrderHistory orderHistory = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            orderHistory = (OrderHistory) session.get(OrderHistory.class, id);
            if (orderHistory != null){
                session.delete(orderHistory);
                result = true;
            }
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
        return result;
    }

    public OrderHistory readOrderHistory(int id) {
        OrderHistory orderHistory = null;
        Session session = null;
        try {
            session = factory.openSession();
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
            session = factory.openSession();
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
}
