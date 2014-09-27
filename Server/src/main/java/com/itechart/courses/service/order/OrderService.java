package com.itechart.courses.service.order;

import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.dto.PageableOrderDTO;
import com.itechart.courses.dto.TableOrderDTO;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.OrderStatusEnum;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 20.09.14.
 */
public interface OrderService {
    public PageableOrderDTO getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums, int first, int count);
    public PageableOrderDTO getAllOrders(int first, int count);
    public PageableOrderDTO searchOrders(OrderSearchDTO parameters, int first, int count);
    public Map<OrderStatusEnum, String> getResolvedOrderStatus(OrderStatusEnum currentStatus);
    public OrderDTO readOrder(int id);
    public void createOrder(OrderDTO orderDTO) throws ParseException;
    public void updateOrder(OrderDTO orderDTO, User currentUser) throws ParseException;


}
