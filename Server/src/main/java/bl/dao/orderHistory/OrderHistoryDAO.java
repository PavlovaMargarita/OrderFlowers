package bl.dao.orderHistory;

import entity.OrderHistory;

/**
 * Created by Александр on 14.08.2014.
 */
public interface OrderHistoryDAO {
    public Integer createOrderHistory(OrderHistory orderHistory);  //return id(Integer) new orderHistory
    public boolean deleteOrderHistory(int id); //if the orderHistory is deleted, the method returns true, otherwise false
    public OrderHistory readOrderHistory(int id); //if the orderHistory is not found, returns null
    public void updateOrderHistory(OrderHistory orderHistory);
}
