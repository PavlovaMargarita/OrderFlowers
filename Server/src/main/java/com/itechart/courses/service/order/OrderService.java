package com.itechart.courses.service.order;

import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.dto.TableOrderDTO;
import com.itechart.courses.enums.OrderStatusEnum;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 20.09.14.
 */
public interface OrderService {
    public List<TableOrderDTO> getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums);
    public List<TableOrderDTO> getAllOrders();
    public List<TableOrderDTO> searchOrders(OrderSearchDTO parameters);
    public Map<OrderStatusEnum, String> getResolvedOrderStatus(OrderStatusEnum currentStatus);
    public OrderDTO readOrder(int id);
}
