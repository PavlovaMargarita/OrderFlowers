package bl.dao.orderHistory;

import entity.Order;
import entity.OrderHistory;

import java.util.List;

/**
 * Created by Александр on 14.08.2014.
 */
public interface OrderHistoryDAO {
    public Integer createOrderHistory(OrderHistory orderHistory);  //return id(Integer) new orderHistory
    public OrderHistory readOrderHistory(int id); //if the orderHistory is not found, returns null
    public void updateOrderHistory(OrderHistory orderHistory);
    public List readOrderHistory(Order order);
}
