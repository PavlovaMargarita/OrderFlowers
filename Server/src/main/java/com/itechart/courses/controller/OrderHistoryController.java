package com.itechart.courses.controller;


import com.itechart.courses.dto.PageableOrderHistoryDTO;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.orderHistory.OrderHistoryService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/OrderFlowers/orderHistory")
public class OrderHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(OrderHistoryController.class);

    @Autowired
    private OrderHistoryService orderHistoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/getOrderHistory")
    public @ResponseBody
    PageableOrderHistoryDTO getOrderHistory(@RequestParam("currentPage") int currentPage,
                                            @RequestParam("pageRecords") int pageRecords ){
        logger.info("User viewed the order history");
        PageableOrderHistoryDTO result = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            result = orderHistoryService.readOrderHistory(firstRecordNumber, pageRecords);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }
}
