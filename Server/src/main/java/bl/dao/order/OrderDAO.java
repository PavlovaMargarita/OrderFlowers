package bl.dao.order;

import entity.Order;

/**
 * Created by Margarita on 14.08.2014.
 */
public interface OrderDAO {
    public Integer createOrder(Order order);  //return id(Integer) new order
    public Order readOrder(int id); //if the order is not found, returns null
    public void updateOrder(Order order);
}
