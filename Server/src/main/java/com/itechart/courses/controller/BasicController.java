package com.itechart.courses.controller;

import com.itechart.courses.dto.*;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.IllegalInputDataException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.authorization.AuthorizationService;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.email.EmailService;
import com.itechart.courses.service.order.OrderService;
import com.itechart.courses.service.orderHistory.OrderHistoryService;
import com.itechart.courses.service.role.RoleService;
import com.itechart.courses.service.template.MessageTemplateService;
import com.itechart.courses.service.user.UserService;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/OrderFlowers")
public class BasicController {

    private static final Logger logger = LoggerFactory.getLogger(BasicController.class);

    @Autowired
    private AuthorizationService authorization;

    @Autowired
    private ContactService  contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private OrderHistoryService orderHistoryService;

    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    @ResponseBody
    public LoginDTO currentUserInfo(Authentication authentication){
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String stringRole = authority.get(0).getAuthority();
        String username = authentication.getName();
        LoginDTO loginDTO = new LoginDTO(RoleEnum.valueOf(stringRole), username);
        return loginDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/authorize")
    public @ResponseBody UserDTO authorization(@RequestBody UserDTO user) throws IOException, JSONException{
        UserDTO userDTO = authorization.execute(user.getLogin(), user.getPassword());
        return userDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactList")
    public @ResponseBody PageableContactDTO getContactList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all contacts");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        PageableContactDTO result = null;
        try {
            result = contactService.readContact(firstRecordNumber, pageRecords);
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactSearch")
    public @ResponseBody PageableContactDTO contactSearch(@RequestBody ContactSearchDTO contactSearchDTO,
                                                          @RequestParam("currentPage") int currentPage,
                                                          @RequestParam("pageRecords") int pageRecords){
        logger.info("User searched contacts");
        PageableContactDTO searchResult = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            searchResult = contactService.searchContact(contactSearchDTO, firstRecordNumber, pageRecords);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return searchResult;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactCorrect")
    public @ResponseBody ContactDTO getContact(@RequestParam("id") String id ){
        logger.info("User viewed the contact");
        ContactDTO contactDTO = null;
        try {
            contactDTO = contactService.readContact(Integer.parseInt(id));
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
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return contactDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCorrect")
    public @ResponseBody void saveContactCorrect(@RequestBody ContactDTO contactDTO) throws IOException{
        logger.info("User updated the contact");
        try {
            contactService.updateContact(contactDTO);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCreate")
    public @ResponseBody void saveContactCreate(@RequestBody ContactDTO contactDTO) throws IOException{
        logger.info("User created new contact");
        try {
            contactService.createContact(contactDTO);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userList")
    public @ResponseBody PageableUserDTO getUserList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all users");
        PageableUserDTO result = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            result = userService.readUser(firstRecordNumber, pageRecords);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/userCorrect")
    public @ResponseBody UserDTO getUser(@RequestParam("id") String id ){
        logger.info("User viewed the user");
        UserDTO result = null;
        try {
            result = userService.readUser(Integer.parseInt(id));
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

    @RequestMapping(method = RequestMethod.GET, value = "/roleEnum")
    public @ResponseBody List getRoleEnum(){
        List result = null;
        try {
            result = roleService.getRoles();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactListForUser")
    public @ResponseBody List<ContactDTO> getContactListForUser(@RequestParam("id") String idUser ){
        logger.info("User viewed all contacts");
        List<ContactDTO> result = null;
        try {
            result = contactService.readContactForUser(Integer.parseInt(idUser));
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

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCorrect")
    public @ResponseBody void saveUserCorrect(@RequestBody UserDTO userDTO){
        logger.info("User updated the user");
        try {
            userService.updateUser(userDTO);
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
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

    @RequestMapping(method = RequestMethod.POST, value = "/saveUserCreate")
    public @ResponseBody void saveUserCreate(@RequestBody UserDTO userDTO) {
        logger.info("User created new user");
        try {
            userService.createUser(userDTO);
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (IllegalArgumentException e){
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

    @RequestMapping(method = RequestMethod.POST, value = "/userDelete")
    public @ResponseBody void userDelete(@RequestBody CheckDTO userId) throws IOException{
        logger.info("User deleted users");
        try {
            for(int i: userId.getCheckId()){
                userService.deleteUser(i);
            }
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

    @RequestMapping(method = RequestMethod.POST, value = "/contactDelete")
    public @ResponseBody boolean contactDelete(@RequestBody CheckDTO contactId) throws IOException{
        boolean delete = true;
        logger.info("User deleted contacts");
        try {
            for(int i: contactId.getCheckId()){
                if(!contactService.deleteContact(i)){
                    delete = false;
                }
            }
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
        return delete;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showEmail")
    public @ResponseBody List<ContactDTO> showEmail(@RequestParam("checkId") ArrayList<Integer> contactId) throws IOException{
        List<ContactDTO> listContacts = new ArrayList<ContactDTO>();
        ContactDTO contact;
        try {
            for(int i: contactId){
                contact = contactService.readContact(i);
                if (contact.getEmail() != null)
                    listContacts.add(contact);
            }
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
        return listContacts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showTemplate")
    public @ResponseBody List<MessageTemplateDTO> showTemplate() throws IOException{
        List<MessageTemplateDTO> result = null;
        try {
           result = messageTemplateService.showTemplate();
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

    @RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
    public @ResponseBody void sendEmail(@RequestBody EmailDTO emailDTO) throws IOException{
        logger.info("User sent email");
        try {
            emailService.sendEmail(emailDTO);
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

    @RequestMapping(method = RequestMethod.POST, value = "/getLogin")
    public @ResponseBody List getLogin() {
        List result = null;
        try {
            result = userService.readLogin();
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


    @RequestMapping(method = RequestMethod.GET, value = "/orderList")
    public @ResponseBody PageableOrderDTO getOrderList(@RequestParam("currentPage") int currentPage,
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
    public @ResponseBody Map<OrderStatusEnum, String> getResolvedOrderStatus(@RequestParam("currentState") OrderStatusEnum currentState){
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
    public @ResponseBody OrderDTO getOrder(@RequestParam("id") String id) {
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


    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsersByRole")
    public @ResponseBody HashMap<String, List<PersonDTO>> getUsersByRole(@RequestParam("role")  List<String> role){
        HashMap<String, List<PersonDTO>> map = null;
        try {
            map = new HashMap<String, List<PersonDTO>>(role.size());
            for (String temp : role){
                map.put(temp, userService.getUsersByRole(RoleEnum.valueOf(temp)));
            }
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
        return map;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/getPerson")
    public @ResponseBody List<PersonDTO> getPerson(@RequestParam("term") String term) {
        List<PersonDTO> listPersonDTO = new ArrayList<PersonDTO>();
        ContactSearchDTO contactSearchDTO = new ContactSearchDTO();
        contactSearchDTO.setSurname(term);
        try {
            if (!term.trim().isEmpty()) {
                listPersonDTO = contactService.searchContact(contactSearchDTO);
            }
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
        return listPersonDTO;
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

    @RequestMapping(method = RequestMethod.GET, value = "/getOrderHistory")
    public @ResponseBody PageableOrderHistoryDTO getOrderHistory(@RequestParam("currentPage") int currentPage,
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
}
