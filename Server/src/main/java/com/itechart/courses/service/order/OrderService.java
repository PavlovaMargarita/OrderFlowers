package com.itechart.courses.service.order;

import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.enums.OrderStatusEnum;

import java.util.List;

/**
 * Created by User on 20.09.14.
 */
public interface OrderService {
    public List<OrderStatusEnum> getResolvedOrderStatus(OrderStatusEnum currentStatus);
    public List<OrderDTO> getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums);
    public List<OrderDTO> getAllOrders();
}
