package com.itechart.courses.service.order;

import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dto.TableOrderDTO;
import com.itechart.courses.entity.Contact;
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
    public List<TableOrderDTO> getAllOrders() {
        List<Order> orders = orderDAO.readAllOrders();
        List<TableOrderDTO> ordersDTO = new ArrayList<TableOrderDTO>();
        for (Order order : orders){
            ordersDTO.add(orderToTableOrderDTO(order));
        }
        return ordersDTO;
    }

    @Override
    public List<TableOrderDTO> getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums) {
        List<Order> orders = orderDAO.readAllOrders(userId, orderStatusEnums);
        List<TableOrderDTO> ordersDTO = new ArrayList<TableOrderDTO>();
        for (Order order : orders){
            ordersDTO.add(orderToTableOrderDTO(order));
        }
        return ordersDTO;
    }

    private List<OrderStatusEnum> getResolvedOrderStatus(OrderStatusEnum currentStatus) {
        return sequenceOrderStatus.getValues(currentStatus);
    }

    private TableOrderDTO orderToTableOrderDTO(Order order){
        TableOrderDTO tableOrderDTO = new TableOrderDTO();
        tableOrderDTO.setId(order.getId());
        tableOrderDTO.setDate(order.getDate());
        tableOrderDTO.setOrderDescription(order.getOrderDescription());
        tableOrderDTO.setSum(order.getSum());
        return tableOrderDTO;
    }
}
