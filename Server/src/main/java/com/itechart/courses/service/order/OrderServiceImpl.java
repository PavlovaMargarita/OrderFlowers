package com.itechart.courses.service.order;

import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.OrderDTO;
import com.itechart.courses.dto.OrderSearchDTO;
import com.itechart.courses.dto.TableOrderDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.service.contact.ContactServiceImpl;
import com.itechart.courses.service.user.UserServiceImpl;
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

    @Override
    public List<TableOrderDTO> searchOrders(OrderSearchDTO parameters) {
        List<Order> orders = orderDAO.searchOrder(parameters);
        List<TableOrderDTO> result = new ArrayList<TableOrderDTO>();
        for (Order order : orders){
            result.add(orderToTableOrderDTO(order));
        }
        return result;
    }


    @Override
    public List<String> getResolvedOrderStatus(OrderStatusEnum currentStatus){
        List<OrderStatusEnum> statusEnums = sequenceOrderStatus.getValues(currentStatus);
        List<String> result = null;
        if (statusEnums != null){
            result = new ArrayList<String>(statusEnums.size());
            for (OrderStatusEnum statusEnum : statusEnums){
                result.add(statusEnum.toRussianStatus());
            }
        }
        return result;
    }

    @Override
    public OrderDTO readOrder(int id) {
        Order order = orderDAO.readOrder(id);
        return orderToOrderDTO(order);
    }

    private TableOrderDTO orderToTableOrderDTO(Order order){
        TableOrderDTO tableOrderDTO = new TableOrderDTO();
        tableOrderDTO.setId(order.getId());
        tableOrderDTO.setDate(order.getDate());
        tableOrderDTO.setOrderDescription(order.getOrderDescription());
        tableOrderDTO.setSum(order.getSum());
        tableOrderDTO.setCustomer(contactToContactDTO(order.getCustomer()));
        tableOrderDTO.setRecipient(contactToContactDTO(order.getRecipient()));
        return tableOrderDTO;
    }

    private OrderDTO orderToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setOrderDescription(order.getOrderDescription());
        orderDTO.setSum(order.getSum());
        orderDTO.setCurrentState(order.getStatus());
        orderDTO.setRussianCurrentState(order.getStatus().toRussianStatus());

        orderDTO.setCustomer(ContactServiceImpl.contactToPersonDTO(order.getCustomer()));
        orderDTO.setRecipient(ContactServiceImpl.contactToPersonDTO(order.getRecipient()));

        orderDTO.setHandlerManager(UserServiceImpl.userToPersonDTO(order.getHandlerManager()));
        orderDTO.setReceiveManager(UserServiceImpl.userToPersonDTO(order.getReceiveManager()));
        orderDTO.setDeliveryManager(UserServiceImpl.userToPersonDTO(order.getDeliveryManager()));
        return orderDTO;
    }

    private ContactDTO contactToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setSurname(contact.getSurname());
        contactDTO.setName(contact.getName());
        return contactDTO;
    }
}
