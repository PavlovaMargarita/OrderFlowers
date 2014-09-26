package com.itechart.courses.dao.order;

import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;

import java.util.List;

public interface OrderDAO {
    public Integer createOrder(Order order);  //return id(Integer) new order
    public Order readOrder(int id); //if the order is not found, returns null
    public void updateOrder(Order order);
    public List readAllOrders();
    public List readAllOrders(int first, int count);
    public List readAllOrder(Contact contact);
    public List<Order> readAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums);
    public List<Order> readAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums, int first, int count);
    public List<Order> searchOrder(OrderSearchDTO parameters);
    public int getOrdersCount();
    public int getOrdersCount(int userId, List<OrderStatusEnum> orderStatusEnums);
}
