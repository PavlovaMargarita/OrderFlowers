package com.itechart.courses.service.order;

import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.dto.PageableOrderDTO;
import com.itechart.courses.dto.TableOrderDTO;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface OrderService {
    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public PageableOrderDTO getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums, int first, int count);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public PageableOrderDTO getAllOrders(int first, int count);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public PageableOrderDTO searchOrders(OrderSearchDTO parameters, int first, int count);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public Map<OrderStatusEnum, String> getResolvedOrderStatus(OrderStatusEnum currentStatus);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public OrderDTO readOrder(int id);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public void createOrder(OrderDTO orderDTO) throws ParseException;

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN, Roles.PROCESSING_ORDERS_SPECIALIST, Roles.SERVICE_DELIVERY_MANAGER})
    public void updateOrder(OrderDTO orderDTO, User currentUser) throws ParseException;


}
