package com.itechart.courses.service.orderHistory;

import com.itechart.courses.dao.orderHistory.OrderHistoryDAO;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.OrderHistoryDTO;
import com.itechart.courses.dto.PageableOrderHistoryDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.OrderHistory;
import com.itechart.courses.service.contact.ContactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 26.09.2014.
 */
@Service
@Transactional
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired(required = true)
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    public PageableOrderHistoryDTO readOrderHistory(int first, int count) {
        List<OrderHistory> orderHistoryList = orderHistoryDAO.readOrderHistory(first, count);
        List ordersDTO = new ArrayList();
        int totalCount = orderHistoryDAO.getOrderHistoryCount();
        for(OrderHistory orderHistory: orderHistoryList){
            ordersDTO.add(orderHistoryToOrderHistoryDTO(orderHistory));
        }
        PageableOrderHistoryDTO result = new PageableOrderHistoryDTO(ordersDTO, totalCount);
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
