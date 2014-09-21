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


    @Override
    public TableOrderDTO getOrder(int orderId) {
        Order order = orderDAO.readOrder(orderId);
        List<OrderStatusEnum> possibleStatues = getResolvedOrderStatus(order.getStatus());
        return orderToTableOrderDTO(order, possibleStatues);
    }



    private List<OrderStatusEnum> getResolvedOrderStatus(OrderStatusEnum currentStatus) {
        return sequenceOrderStatus.getValues(currentStatus);
    }

    private OrderDTO orderToOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setDate(order.getDate());
        orderDTO.setOrderDescription(order.getOrderDescription());
        orderDTO.setSum(order.getSum());
        return orderDTO;
    }

    private TableOrderDTO orderToTableOrderDTO(Order order, List<OrderStatusEnum> possibleStatuses){
        TableOrderDTO orderDTO = new TableOrderDTO();
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
