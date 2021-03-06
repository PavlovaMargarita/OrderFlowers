package com.itechart.courses.service.order;

import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dao.orderHistory.OrderHistoryDAO;
import com.itechart.courses.dto.*;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.OrderHistory;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.service.contact.ContactServiceImpl;
import com.itechart.courses.service.user.UserServiceImpl;
import com.itechart.courses.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired (required = true)
    private SequenceOrderStatus sequenceOrderStatus;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    public PageableOrderDTO getAllOrders(int first, int count) {
        List<Order> orders = orderDAO.readAllOrders(first, count);
        List<TableOrderDTO> ordersDTO = new ArrayList<TableOrderDTO>();
        int totalCount = orderDAO.getOrdersCount();
        for (Order order : orders){
            ordersDTO.add(orderToTableOrderDTO(order));
        }
        PageableOrderDTO result = new PageableOrderDTO(ordersDTO, totalCount);
        return result;
    }


    @Override
    public PageableOrderDTO getAllOrders(int userId, List<OrderStatusEnum> orderStatusEnums, int first, int count) {
        List<Order> orders = orderDAO.readAllOrders(userId, orderStatusEnums, first, count);
        List<TableOrderDTO> ordersDTO = new ArrayList<TableOrderDTO>();
        int totalCount = orderDAO.getOrdersCount(userId, orderStatusEnums);
        for (Order order : orders){
            ordersDTO.add(orderToTableOrderDTO(order));
        }
        PageableOrderDTO result = new PageableOrderDTO(ordersDTO, totalCount);
        return result;
    }

    @Override
    public PageableOrderDTO searchOrders(OrderSearchDTO parameters, int first, int count) {
        List<Order> orders = orderDAO.searchOrder(parameters, first, count);
        List<TableOrderDTO> ordersDTO = new ArrayList<TableOrderDTO>();
        int totalCount = orderDAO.getOrderCount(parameters);
        for (Order order : orders){
            ordersDTO.add(orderToTableOrderDTO(order));
        }
        PageableOrderDTO result = new PageableOrderDTO(ordersDTO, totalCount);
        return result;
    }

    @Override
    public Map<OrderStatusEnum, String> getResolvedOrderStatus(OrderStatusEnum currentStatus){
        List<OrderStatusEnum> statusEnums = sequenceOrderStatus.getValues(currentStatus);
        Map<OrderStatusEnum, String> result = null;
        if (statusEnums != null){
            result = new HashMap<OrderStatusEnum, String>(statusEnums.size());
            for (OrderStatusEnum statusEnum : statusEnums){
                result.put(statusEnum, statusEnum.toRussianStatus());
            }
        }
        return result;
    }

    @Override
    public OrderDTO readOrder(int id) {
        Order order = orderDAO.readOrder(id);
        return orderToOrderDTO(order);
    }


    @Override
    public void createOrder(OrderDTO orderDTO) throws ParseException {
        if(Validation.validateOrder(orderDTO)) {
            Order order = new Order();
            order.setStatus(OrderStatusEnum.NEW);
            order.setOrderDescription(orderDTO.getOrderDescription());
            order.setSum(orderDTO.getSum());
            order.setDate(convertToDate(orderDTO.getDate()));

            User receiveManager = new User();
            receiveManager.setId(orderDTO.getReceiveManager().getId());
            User deliveryManager = new User();
            deliveryManager.setId(orderDTO.getDeliveryManager().getId());
            User handlerManager = new User();
            handlerManager.setId(orderDTO.getHandlerManager().getId());
            Contact recipient = new Contact();
            Contact customer = new Contact();
            recipient.setId(orderDTO.getRecipient().getId());
            customer.setId(orderDTO.getCustomer().getId());

            order.setDeliveryManager(deliveryManager);
            order.setHandlerManager(handlerManager);
            order.setReceiveManager(receiveManager);
            order.setRecipient(recipient);
            order.setCustomer(customer);
            orderDAO.createOrder(order);

            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setChangeDate(order.getDate());
            orderHistory.setStatus(order.getStatus());
            orderHistory.setUser(receiveManager);
            orderHistory.setOrder(order);
            orderHistoryDAO.createOrderHistory(orderHistory);
        }
        else {
            throw new IllegalArgumentException("incorrect orderDTO");
        }
    }

    @Override
    public void updateOrder(OrderDTO orderDTO, User currentUser) throws ParseException {
        if (Validation.validateOrder(orderDTO)) {
            Order order = orderDAO.readOrder(orderDTO.getId());

            if (currentUser.getRole() == RoleEnum.ROLE_SUPERVISOR || currentUser.getRole() == RoleEnum.ROLE_RECEIVING_ORDERS_MANAGER) {
                Contact customer = new Contact();
                Contact recipient = new Contact();
                customer.setId(orderDTO.getCustomer().getId());
                recipient.setId(orderDTO.getRecipient().getId());

                User deliveryManager = new User();
                User handlerManager = new User();
                deliveryManager.setId(orderDTO.getDeliveryManager().getId());
                handlerManager.setId(orderDTO.getHandlerManager().getId());

                order.setCustomer(customer);
                order.setRecipient(recipient);
                order.setDeliveryManager(deliveryManager);
                order.setHandlerManager(handlerManager);
            }

            order.setOrderDescription(orderDTO.getOrderDescription());
            order.setSum(orderDTO.getSum());
            order.setOrderHistory(null);

            OrderStatusEnum newOrderStatus = null;
            if (orderDTO.getRussianCurrentState() != null) {
                newOrderStatus = OrderStatusEnum.valueOf(orderDTO.getRussianCurrentState());
            }

            if (newOrderStatus != null && order.getStatus() != newOrderStatus) {
                order.setStatus(newOrderStatus);
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setStatus(newOrderStatus);
                String statusComment = orderDTO.getStatusComment();
                if (statusComment != null) {
                    if ((statusComment = statusComment.trim()).isEmpty()) {
                        statusComment = null;
                    }
                }
                orderHistory.setComment(statusComment);
                Order temp = new Order();
                temp.setId(order.getId());
                User user = new User();
                user.setId(currentUser.getId());
                orderHistory.setOrder(temp);
                orderHistory.setUser(user);
                orderHistory.setChangeDate(convertToDate(orderDTO.getDate()));
                orderHistoryDAO.createOrderHistory(orderHistory);
            }
            orderDAO.updateOrder(order);
        }
        else {
            throw new IllegalArgumentException("incorrect orderDTO");
        }
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
        contactDTO.setPatronymic(contact.getPatronymic());
        return contactDTO;
    }


    private Date convertToDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsed = format.parse(date);
        return new java.sql.Date(parsed.getTime());
    }
}
