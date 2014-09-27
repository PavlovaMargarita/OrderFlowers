package com.itechart.courses.dao.orderHistory;

import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.OrderHistory;

import java.util.List;

public interface OrderHistoryDAO {
    public Integer createOrderHistory(OrderHistory orderHistory);  //return id(Integer) new orderHistory
    public OrderHistory readOrderHistory(int id); //if the orderHistory is not found, returns null
    public void updateOrderHistory(OrderHistory orderHistory);
    public List readOrderHistory(Order order);
    public List readOrderHistory(int first, int count);
    public int getOrderHistoryCount();
}
