package com.itechart.courses.service.order;

import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dto.OrderDTO;
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


    @Override
    public OrderDTO getOrder(int orderId) {
        Order order = orderDAO.readOrder(orderId);
        List<OrderStatusEnum> possibleStatues = getResolvedOrderStatus(order.getStatus());
        return orderToOrderDTO(order, possibleStatues);
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

    private OrderDTO orderToOrderDTO(Order order, List<OrderStatusEnum> possibleStatuses){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDescription(order.getOrderDescription());
        orderDTO.setCurrentState(order.getStatus().toRussianStatus());
        orderDTO.setSum(order.getSum());

        if (possibleStatuses != null){
            List<String> stringPossibleStatuses = new ArrayList<String>(possibleStatuses.size());
            for (OrderStatusEnum statusEnum : possibleStatuses){
                stringPossibleStatuses.add(statusEnum.toRussianStatus());
            }
            orderDTO.setPossibleStates(stringPossibleStatuses);
        }

        String surname = order.getCustomer().getSurname();
        String name = order.getCustomer().getName();
        String patronymic = order.getCustomer().getPatronymic();
        StringBuilder builder = new StringBuilder();
        if (surname != null){
            builder.append(surname);
        }
        if (name != null){
            builder.append(" ").append(name);
        }
        if (patronymic != null){
            builder.append(" ").append(patronymic);
        }
        orderDTO.setCustomer(builder.toString());
        builder.setLength(0);

        Contact contact = order.getHandlerManager().getContact();
        surname = contact.getSurname();
        name = contact.getName();
        patronymic = contact.getPatronymic();
        if (surname != null){
            builder.append(surname);
        }
        if (name != null){
            builder.append(" ").append(name);
        }
        if (patronymic != null){
            builder.append(" ").append(patronymic);
        }
        orderDTO.setHandlerManager(builder.toString());
        builder.setLength(0);

        contact = order.getReceiveManager().getContact();
        surname = contact.getSurname();
        name = contact.getName();
        patronymic = contact.getPatronymic();
        if (surname != null){
            builder.append(surname);
        }
        if (name != null){
            builder.append(" ").append(name);
        }
        if (patronymic != null){
            builder.append(" ").append(patronymic);
        }
        orderDTO.setReceiveManager(builder.toString());
        builder.setLength(0);

        contact = order.getDeliveryManager().getContact();
        surname = contact.getSurname();
        name = contact.getName();
        patronymic = contact.getPatronymic();
        if (surname != null){
            builder.append(surname);
        }
        if (name != null){
            builder.append(" ").append(name);
        }
        if (patronymic != null){
            builder.append(" ").append(patronymic);
        }
        orderDTO.setDeliveryManager(builder.toString());
        builder.setLength(0);

        contact = order.getRecipient();
        surname = contact.getSurname();
        name = contact.getName();
        patronymic = contact.getPatronymic();
        if (surname != null){
            builder.append(surname);
        }
        if (name != null){
            builder.append(" ").append(name);
        }
        if (patronymic != null){
            builder.append(" ").append(patronymic);
        }
        orderDTO.setRecipient(builder.toString());
        return orderDTO;
    }
}
