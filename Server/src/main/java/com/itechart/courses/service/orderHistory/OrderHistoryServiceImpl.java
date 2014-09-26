package com.itechart.courses.service.orderHistory;

import com.itechart.courses.dao.orderHistory.OrderHistoryDAO;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.OrderHistoryDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.OrderHistory;
import com.itechart.courses.service.contact.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 26.09.2014.
 */
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired(required = true)
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    public List readOrderHistory() {
        List<OrderHistory> orderHistoryList = orderHistoryDAO.readOrderHistory();
        List result = new ArrayList();
        for(OrderHistory orderHistory: orderHistoryList){
            result.add(orderHistoryToOrderHistoryDTO(orderHistory));
        }
        return result;
    }

    private OrderHistoryDTO orderHistoryToOrderHistoryDTO(OrderHistory orderHistory){
        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
        orderHistoryDTO.setCustomer(ContactServiceImpl.contactToPersonDTO(orderHistory.getOrder().getCustomer()));
        orderHistoryDTO.setRecipient(ContactServiceImpl.contactToPersonDTO(orderHistory.getOrder().getRecipient()));
        orderHistoryDTO.setRussianOrderStatus(orderHistory.getStatus().toRussianStatus());
        orderHistoryDTO.setDate(orderHistory.getChangeDate());
        orderHistoryDTO.setComment(orderHistory.getComment());
        return orderHistoryDTO;
    }
}
