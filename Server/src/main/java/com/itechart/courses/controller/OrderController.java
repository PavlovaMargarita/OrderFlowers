package com.itechart.courses.controller;


import com.itechart.courses.dto.*;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.IllegalInputDataException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.order.OrderService;
import com.itechart.courses.service.user.UserService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/OrderFlowers/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/orderList")
    public @ResponseBody
    PageableOrderDTO getOrderList(@RequestParam("currentPage") int currentPage,
                                  @RequestParam("pageRecords") int pageRecords,
                                  Authentication authentication){
        logger.info("User viewed all orders");
        if (authentication == null){
            throw new NotAuthorizedException();
        }
        LoginDTO dto = currentUserInfo(authentication);

        com.itechart.courses.entity.User user = null;
        List<OrderStatusEnum> statusEnums = null;
        PageableOrderDTO orders = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            switch (dto.getRole()){
                case ROLE_PROCESSING_ORDERS_SPECIALIST:
                    user = userService.readUser(dto.getLogin());
                    statusEnums = new ArrayList<OrderStatusEnum>(2);
                    statusEnums.add(OrderStatusEnum.ADOPTED);
                    statusEnums.add(OrderStatusEnum.IN_PROCESSING);
                    orders = orderService.getAllOrders(user.getId(), statusEnums, firstRecordNumber, pageRecords);
                    break;
                case ROLE_SERVICE_DELIVERY_MANAGER:
                    user = userService.readUser(dto.getLogin());
                    statusEnums = new ArrayList<OrderStatusEnum>(2);
                    statusEnums.add(OrderStatusEnum.READY_FOR_SHIPPING);
                    statusEnums.add(OrderStatusEnum.SHIPPING);
                    orders = orderService.getAllOrders(user.getId(), statusEnums, firstRecordNumber, pageRecords);
                    break;
                case ROLE_RECEIVING_ORDERS_MANAGER:
                    orders = orderService.getAllOrders(firstRecordNumber, pageRecords);
                    break;
                case ROLE_SUPERVISOR:
                    orders = orderService.getAllOrders(firstRecordNumber, pageRecords);
                    break;
                case ROLE_ADMIN:
                    orders = orderService.getAllOrders(firstRecordNumber, pageRecords);
                    break;
            }
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
        return orders;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/orderSearch")
    public @ResponseBody PageableOrderDTO searchOrder(@RequestBody OrderSearchDTO parameters,
                                                      @RequestParam("currentPage") int currentPage,
                                                      @RequestParam("pageRecords") int pageRecords){

        logger.info("User searched orders");
        PageableOrderDTO result = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            result = orderService.searchOrders(parameters, firstRecordNumber, pageRecords);
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
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

    @RequestMapping(method = RequestMethod.GET, value = "/getResolvedOrderState")
    public @ResponseBody
    Map<OrderStatusEnum, String> getResolvedOrderStatus(@RequestParam("currentState") OrderStatusEnum currentState){
        Map<OrderStatusEnum, String> result = null;
        try {
            result = orderService.getResolvedOrderStatus(currentState);
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

    @RequestMapping(method = RequestMethod.GET, value = "/showOrder")
    public @ResponseBody
    OrderDTO getOrder(@RequestParam("id") String id) {
        logger.info("User viewed the order");
        OrderDTO result = null;
        try {
            result = orderService.readOrder(Integer.parseInt(id));
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (NumberFormatException e){
            throw new IllegalInputDataException();
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

    @RequestMapping(method = RequestMethod.POST, value = "/correctOrder")
    public @ResponseBody void correctOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) throws ParseException {
        logger.info("User updated the order");
        if (authentication == null){
            throw new NotAuthorizedException();
        }
        LoginDTO loginDTO = currentUserInfo(authentication);
        try {
            com.itechart.courses.entity.User user = userService.readUser(loginDTO.getLogin());
            orderService.updateOrder(orderDTO, user);
        }
        catch (ParseException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
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
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createOrder")
    public @ResponseBody void createOrder(@RequestBody OrderDTO orderDTO, Authentication authentication) throws ParseException {
        logger.info("User created new order");
        if (authentication == null){
            throw new NotAuthorizedException();
        }
        LoginDTO loginDTO = currentUserInfo(authentication);
        try {
            com.itechart.courses.entity.User user = userService.readUser(loginDTO.getLogin());
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(user.getId());
            orderDTO.setReceiveManager(personDTO);
            orderService.createOrder(orderDTO);
        }
        catch (ParseException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
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
    }

    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }

    public LoginDTO currentUserInfo(Authentication authentication){
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String stringRole = authority.get(0).getAuthority();
        String username = authentication.getName();
        LoginDTO loginDTO = new LoginDTO(RoleEnum.valueOf(stringRole), username);
        return loginDTO;
    }
}
