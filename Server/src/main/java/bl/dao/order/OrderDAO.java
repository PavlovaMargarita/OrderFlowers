package bl.dao.order;

import entity.Contact;
import entity.Order;

import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */
public interface OrderDAO {
    public Integer createOrder(Order order);  //return id(Integer) new order
    public Order readOrder(int id); //if the order is not found, returns null
    public void updateOrder(Order order);
    public List readAllOrders();
    public List readAllOrder(Contact contact);
}
