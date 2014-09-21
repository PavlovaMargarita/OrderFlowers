package com.itechart.courses.service.order;

import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 20.09.14.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired (required = true)
    private SequenceOrderStatus sequenceOrderStatus;

    @Autowired
    private OrderDAO orderDAO;


    @Override
    public List<OrderStatusEnum> getResolvedOrderStatus(OrderStatusEnum currentStatus) {
        return sequenceOrderStatus.getValues(currentStatus);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderDAO.readAllOrders();
        List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
        for (Order order : orders){
            ordersDTO.add(orderToOrderDTO(order));
        }
        return ordersDTO;
    }

    @Override
    public List<OrderDTO> getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums) {
        List<Order> orders = orderDAO.readAllOrders(userId, orderStatusEnums);
        List<OrderDTO> ordersDTO = new ArrayList<OrderDTO>();
        for (Order order : orders){
            ordersDTO.add(orderToOrderDTO(order));
        }
        return ordersDTO;
    }


    private OrderDTO orderToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDate(order.getDate());
        orderDTO.setOrderDescription(order.getOrderDescription());
        orderDTO.setSum(order.getSum());
        return orderDTO;
    }
}
